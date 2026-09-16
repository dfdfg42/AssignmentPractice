# Windows PowerShell port of reproduce-lost-update.sh
# Usage (from repo root):
#   powershell -ExecutionPolicy Bypass -File lab1\scripts\reproduce-lost-update.ps1
#   powershell -ExecutionPolicy Bypass -File lab1\scripts\reproduce-lost-update.ps1 -Rounds 2000 -Concurrency 100
[CmdletBinding()]
param(
    [int]$Rounds = $(if ($env:ROUNDS) { [int]$env:ROUNDS } else { 1000 }),
    [int]$Concurrency = $(if ($env:CONCURRENCY) { [int]$env:CONCURRENCY } else { 50 }),
    [string]$BaseUrl = $(if ($env:BASE_URL) { $env:BASE_URL } else { 'http://localhost:8080' })
)

$ErrorActionPreference = 'Stop'

try {
    $post = Invoke-RestMethod -Method Post -Uri "$BaseUrl/posts/reset"
} catch {
    Write-Host "Failed to reach $BaseUrl - is the app running? ($($_.Exception.Message))"
    exit 1
}

if (-not $post.id) {
    Write-Host "Failed to create a post: $($post | ConvertTo-Json -Compress)"
    exit 1
}
$postId = $post.id

Write-Host "Initial post: id=$($post.id) likes=$($post.likes) comments=$($post.comments)"
Write-Host "Sending $Rounds likes and $Rounds comments with concurrency=$Concurrency ..."

Add-Type -AssemblyName System.Net.Http
# .NET Framework caps concurrent connections per host at 2 unless raised
[System.Net.ServicePointManager]::DefaultConnectionLimit = [Math]::Max(100, $Concurrency * 2)

$client = New-Object System.Net.Http.HttpClient
$client.Timeout = [TimeSpan]::FromSeconds(30)

$likesUrl = "$BaseUrl/posts/$postId/likes"
$commentsUrl = "$BaseUrl/posts/$postId/comments"

$all = New-Object System.Collections.Generic.List[object]
$pending = New-Object System.Collections.Generic.List[System.Threading.Tasks.Task]

for ($i = 1; $i -le $Rounds; $i++) {
    foreach ($url in $likesUrl, $commentsUrl) {
        while ($pending.Count -ge $Concurrency) {
            $done = [System.Threading.Tasks.Task]::WaitAny($pending.ToArray())
            $pending.RemoveAt($done)
        }
        $request = New-Object System.Net.Http.HttpRequestMessage([System.Net.Http.HttpMethod]::Post, $url)
        $task = $client.SendAsync($request)
        $all.Add($task)
        $pending.Add($task)
    }
    if ($i % 200 -eq 0) { Write-Host "  ... $i / $Rounds rounds sent" }
}

try { [System.Threading.Tasks.Task]::WaitAll($pending.ToArray()) } catch {}

$failed = @($all | Where-Object { $_.IsFaulted -or $_.IsCanceled -or -not $_.Result.IsSuccessStatusCode }).Count
if ($failed -gt 0) { Write-Host "Warning: $failed of $($all.Count) requests failed" }
$client.Dispose()

$final = Invoke-RestMethod -Uri "$BaseUrl/posts/$postId"
Write-Host "Final post:   id=$($final.id) likes=$($final.likes) comments=$($final.comments)"
Write-Host "Expected:     likes=$Rounds comments=$Rounds"

if ($final.likes -eq $Rounds -and $final.comments -eq $Rounds) {
    Write-Host 'Lost Update was not reproduced. Run the script again.'
    exit 1
}

Write-Host "Lost Update reproduced: actual likes=$($final.likes) comments=$($final.comments)"
