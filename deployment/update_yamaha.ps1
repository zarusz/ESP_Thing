param([string]$dev="proto")

$remote_session = "pi"
$binding_file = "org.openhab.binding.yamahareceiver-2.2.0-SNAPSHOT.jar"

Write-Host "Copy file $binding_file"
Copy-Item  "e:\dev\mygithub\openhab2-addons-zarusz\addons\binding\org.openhab.binding.yamahareceiver\target\$binding_file" "\\RASPBERRYPI\pi" -ErrorAction Stop

Write-Host "Copy to OpenHAB"
$remote_cmd = "sudo cp $binding_file /usr/share/openhab2/addons"
& "plink" $remote_session $remote_cmd

Write-Host "Change owner"
$remote_cmd = "sudo chown openhab:openhab /usr/share/openhab2/addons/$binding_file"
& "plink" $remote_session $remote_cmd
