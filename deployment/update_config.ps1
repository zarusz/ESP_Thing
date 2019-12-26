param([string]$dev="proto")

$topic = "dev/$dev/service/config"
$user = "openhab"
$pass = "y1AqqFx5ZNz3"
$config = Get-Content "../thing/data/config.json" -Raw
$config = $config.Replace("`r`n","")
#$config = "{ `"UniqueId`": `"proto`" }"
#$config = "{ `"UniqueId`": `"proto`", `"WifiName`": `"WareHouse_24GHz`" }"


Write-Host $config

Write-Host "Sending config update command to $dev"
$remote_cmd = "mosquitto_pub -t $topic -u $user -P $pass -m '$config'"
Write-Host $remote_cmd
$remote_session = "pi"
& "plink" $remote_session $remote_cmd
