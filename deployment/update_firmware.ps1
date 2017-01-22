param([string]$dev="proto")

$source_firmware = "firmware.bin"
$target_firmware = "firmware_$dev.bin"

Write-Host "Copying firmware $target_firmware"
Copy-Item  "..\thing\.pioenvs\esp12e\$source_firmware" "C:\inetpub\wwwroot\$target_firmware" -ErrorAction Stop

$topic = "dev/$dev/service/upgrade"
$url = "http://192.168.1.121/$target_firmware"
$user = "openhab"
$pass = "y1AqqFx5ZNz3"

Write-Host "Sending upgrade command to $dev"
$remote_cmd = "mosquitto_pub -t $topic -m $url -u $user -P $pass"
$remote_session = "pi"
& "plink" $remote_session $remote_cmd
