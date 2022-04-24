$Out = (Get-Content $PSScriptRoot\out.txt -Encoding UTF8).Trim().ToLower()
$Exp = (Get-Content $PSScriptRoot\exp.txt -Encoding UTF8).Trim().ToLower()

$FullList = $Out + $Exp

$report = @()
$ExistsInOut = $true
$ExistsInExp = $true
$count = 0
$countOut = (Get-Content $PSScriptRoot\out.txt).Length
$first = $true
foreach($item in $FullList) {
    $count = $count +1
    if($count.Equals($countOut+1)){
        if($first){
        $first = $false
        $count = 1
        }
    }
    If($Out.Contains($item)) {
        $ExistsInOut = $true
    }
    else {
        $ExistsInOut = $false
    }
    
    If($Exp.Contains($item)) {
        $ExistsInExp = $true
    }
    else {
        $ExistsInExp = $false
    }
    
    If($ExistsInExp -ne $ExistsInOut) {
    $report += New-Object psobject -Property @{Line=$count; Txt=$Item; Out=$ExistsInOut; Exp=$ExistsInExp}
    }
}

$report | select Line, Txt, Out, Exp | Out-File $PSScriptRoot\result.txt -Encoding UTF8