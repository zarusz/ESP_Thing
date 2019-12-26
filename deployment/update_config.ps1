param([string]$dev="proto", [string]user, [string]$pass)

$topic = "dev/$dev/service/config"
$config = Get-Content "../thing/data/config.json" -Raw
$config = $config.Replace("`r`n","")

Write-Host $config

Write-Host "Sending config update command to $dev"
$remote_cmd = "mosquitto_pub -t $topic -u $user -P $pass -m '$config'"
Write-Host $remote_cmd
$remote_session = "pi"
& "plink" $remote_session $remote_cmd
