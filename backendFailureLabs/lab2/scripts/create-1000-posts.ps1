# Windows PowerShell port of create-1000-posts.sh
# Usage (from repo root):
#   powershell -ExecutionPolicy Bypass -File lab2\scripts\create-1000-posts.ps1
#   powershell -ExecutionPolicy Bypass -File lab2\scripts\create-1000-posts.ps1 -Count 5000
#   powershell -ExecutionPolicy Bypass -File lab2\scripts\create-1000-posts.ps1 -Jdbc   (JdbcTemplate batchUpdate)
[CmdletBinding()]
param(
    [int]$Count = $(if ($env:COUNT) { [int]$env:COUNT } else { 1000 }),
    [string]$BaseUrl = $(if ($env:BASE_URL) { $env:BASE_URL } else { 'http://localhost:8080' }),
    [switch]$Jdbc
)

$ErrorActionPreference = 'Stop'

Write-Host "Creating $Count posts with a single HTTP request ..."

$path = if ($Jdbc) { '/posts/bulk-jdbc' } else { '/posts/bulk' }

try {
    $result = Invoke-RestMethod -Method Post -Uri "$BaseUrl${path}?count=$Count" -TimeoutSec 0
} catch {
    Write-Host "Failed to reach $BaseUrl - is the app running? ($($_.Exception.Message))"
    exit 1
}

Write-Host "requestedCount=$($result.requestedCount) savedCount=$($result.savedCount) elapsedMillis=$($result.elapsedMillis) method=$($result.method)"
