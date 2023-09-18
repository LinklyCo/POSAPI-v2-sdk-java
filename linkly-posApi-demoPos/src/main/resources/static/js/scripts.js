
//function disableF5(e) { if ((e.which || e.keyCode) == 116 || (e.which || e.keyCode) == 82) e.preventDefault(); };
//document.addEventListener("keydown", disableF5);
//document.addEventListener('contextmenu', e => e.preventDefault());

getResponses();
getLogs();

var isTraceEnabled = false;
var checkbox = document.querySelector("input[name='trace']");
checkbox.addEventListener('change', function() {
  isTraceEnabled = this.checked;
});

function getLogs() {
    if(isTraceEnabled){
        const url = "http://localhost:8080/logs"
        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.send(null);
        xhr.onreadystatechange = function() {
            if (xhr.readyState == XMLHttpRequest.DONE) {
                JSON.parse(xhr.responseText).forEach(function(obj) {
                    populateLogs(obj);
                });
            }
        }
    }
    setTimeout(getLogs, 500);
}


function getResponses() {
    const url = "http://localhost:8080/responses"
    var xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.send(null);
    
    xhr.onreadystatechange = function() {
        if (xhr.readyState == XMLHttpRequest.DONE) {
            if(xhr.responseText.length > 2){
                var jsonArr = JSON.parse(xhr.responseText);
                let sessionUuids = Array.from(document.querySelector('#session select[name="sessionId"]')
                    .options).map(option => option.text);
                for(var key in jsonArr) {
                    var json = jsonArr[key];
                    var requestType = json['requestType'];
                    var responseType = json['responseType'].toUpperCase();
                    var body = json['response'];
                    var uuid = json['uuid'];
                    
                    if(uuid && !sessionUuids.includes(uuid)){
                        refreshSessionOptions(uuid);
                        sessionUuids.push(uuid);
                    }
                    
                    switch(responseType) {
                      case 'ERROR':
                        var text = 'Error: ';
                        if(body['httpStatusCode']){
                            text += ' ' + body['httpStatusCode'];
                        }
                        if(body['message']) {
                            text += ', ' + body['message'];
                        }
                        if(body['message'] !== 'Session Complete'){
                            alert(text);
                            populateLogs(text);
                        }
                        enableButtons();
                      break;
                      
                      case 'PAIRING':
                        var username = document.querySelector('#pairing input[name="username"]').value;
                        var select = document.querySelector('#home select[name="users"]');
                        
                        var userList = Array.from(select.options).map(option => option.text);
                        if(!userList.includes(username)){
                            var newOption = new Option(username, username);
                            newOption.setAttribute('selected', true);
                            select.add(newOption, undefined);
                        } else {
                            var myIdex = document.querySelector('#home select[name="users"] option[value="' 
                                + username + '"]').index;
                            select.selectedIndex = myIdex;
                        }
                        changeLane(username);
                        displayHome();
                        populateLogs('Pairing Complete!');
                        document.querySelector('#pairing input[name="username"]').value = '';
                        document.querySelector('#pairing input[name="password"]').value = '';
                        document.querySelector('#pairing input[name="pairCode"]').value = '';
                        document.querySelector('#session input[name="username"]').value = username;
                      break;
                      
                      case 'LOGON':
                        populateResult(body);
                        populateLogs('LogonComplete: ' + body['responseText'].replace(/\s/g, '&nbsp;'));
                        document.querySelector('.approveBox').style.display = 'none';
                        enableButtons();
                      break;
                      
                      case 'DISPLAY':
                        var displayArr = body['displayText'];
                        var displayText = "";
                        for(var display of displayArr){
                            displayText += display.replace(/\s/g, '&nbsp;') + '<br />';
                        }
                        populateLogs('Display: <br />' + displayText);
                        
                        if('RESULTREQUEST' === requestType.toUpperCase()){
                            enableButtons();
                            populateResult(body);
                            populateLogs('Display');
                            populateLogs('Done!');
                            return;
                        }
                        
                        var boxContent = displayArr[0].trim() + '<br />' + displayArr[1].trim();
                        document.querySelector('.approveBox p').innerHTML = boxContent;
                        
                        var btnDisplay = '';
                        var key = '0';
                        if(body['cancelKeyFlag'] === true){
                            btnDisplay = 'Cancel';
                        }
                        if(body['okKeyFlag'] === true) {
                            btnDisplay = 'OK';
                        }
                        if(body['acceptYesKeyFlag'] === true) {
                            btnDisplay = 'YES';
                        }
                        document.querySelector('.approveBox').style.display = 'block';
                        
                        var btn = document.querySelector('.approveBox button');
                        if(btnDisplay.length > 1){
                            btn.setAttribute('data-key', key);
                            btn.style.display = '';
                            btn.setAttribute('data-sessionId', uuid);
                            btn.innerHTML = btnDisplay;
                        } else {
                            btn.style.display = 'none';
                        }
                      break;
                      
                      case 'RECEIPT':
                        var receiptArr = body['receiptText'];
                        var receiptText = "Receipt: <br />";
                        for(var receipt of receiptArr){
                            receiptText += receipt.replace(/\s/g, '&nbsp;') + '<br />';
                        }
                        document.getElementById('receipt').innerHTML = receiptText;
                        populateLogs(receiptText);
                      break;
                      
                      case 'SETTLEMENT':
                        populateResult(body);
                        populateLogs('SettlementComplete: ' + body['responseText'].replace(/\s/g, '&nbsp;'));
                        document.querySelector('.approveBox').style.display = 'none';
                        enableButtons();
                      break;
                      
                      case 'QUERYCARD':
                        populateResult(body);
                        populateLogs('QueryCardComplete: ' + body['responseText'].replace(/\s/g, '&nbsp;'));
                        document.querySelector('.approveBox').style.display = 'none';
                        enableButtons();
                      break;
                      
                      case 'STATUS':
                        populateResult(body);
                        populateLogs('StatusComplete: ' + body['responseText'].replace(/\s/g, '&nbsp;'));
                        document.querySelector('.approveBox').style.display = 'none';
                        enableButtons();
                      break;
                      
                      case 'REPRINTRECEIPT':
                        populateResult(body);
                        populateLogs('ReprintReceiptComplete: ' + body['responseText'].replace(/\s/g, '&nbsp;'));
                        document.querySelector('.approveBox').style.display = 'none';
                        enableButtons();
                      break;
                      
                      case 'CONFIGUREMERCHANT':
                        populateResult(body);
                        populateLogs('ConfigureMerchantComplete: ' + body['responseText'].replace(/\s/g, '&nbsp;'));
                        document.querySelector('.approveBox').style.display = 'none';
                        enableButtons();
                      break;
                      
                      case 'TRANSACTION':
                        populateResult(body);
                        populateLogs('TransactionComplete: ' + body['responseText'].replace(/\s/g, '&nbsp;'));
                        document.querySelector('.approveBox').style.display = 'none';
                        enableButtons();
                      break;
                      
                      default:
                        console.log('Invalid Transaction Response Type');
                    }
                }
            }
        }
    }
    setTimeout(getResponses, 1000);
}

var usernameLength = document.querySelector('select[name="users"]').options.length;
if(!usernameLength){
    document.getElementById('home').style.display = 'none';
    var processDiv = document.getElementById('pairing');
    processDiv.style.display = 'block';
}

document.getElementsByClassName('pairingGo')[0].addEventListener('click', function(){
    var username = document.querySelector('#pairing input[name="username"]').value;
    var password = document.querySelector('#pairing input[name="password"]').value;
    var pairCode = document.querySelector('#pairing input[name="pairCode"]').value;
    
    if(username && password && pairCode){
        disableButtons();
        let request = {
            username: username,
            password: password,
            pairCode: pairCode
        };
        let body = JSON.stringify(request);
        post('pairingRequest', body);
        
    } else {
        alert('Please fill all required.')
    }
});

document.getElementsByClassName('logonGo')[0].addEventListener('click', function(){
    var logonType = document.querySelector('#logon select[name="logonType"]').value;
    populateLogs('Logon requested. Type: ' + logonType);
    disableButtons();
    let request = {
        logonType: logonType,
    };
    var padRequest = buildPadReqeuest();
    var posApiRequest = buildPosApiRequestBody();
    request = {...request, ...posApiRequest};
    if(Object.keys(padRequest).length > 0){
        request = {...request, purchaseAnalysisData: padRequest};
    }
    let body = JSON.stringify(request);
    post('logon', body);
});

document.getElementsByClassName('sessionGo')[0].addEventListener('click', function(){
    var sessionId = document.querySelector('#session select[name="sessionId"]').value;
    if(sessionId){
        populateLogs('Session Id: ' + sessionId);
        disableButtons();
        let request = {
            sessionId: sessionId
        };
        let body = JSON.stringify(request);
        post('result', body);
        
    } else {
        alert('Please select sessionId.');
    }
});

document.getElementsByClassName('settlementGo')[0].addEventListener('click', function(){
    var settlementType = document.querySelector('#settlement select[name="settlementType"]').value;
    if(settlementType){
        populateLogs('Settlement requested type: ' + settlementType);
        disableButtons();
        let request = {
            settlementType: settlementType
        };
        var padRequest = buildPadReqeuest();
        var posApiRequest = buildPosApiRequestBody();
        request = {...request, ...posApiRequest};
        if(Object.keys(padRequest).length > 0){
            request = {...request, purchaseAnalysisData: padRequest};
        }
        let body = JSON.stringify(request);
        post('settlement', body);
        
    } else {
        alert('Please select Settlement Type.');
    }
});

document.getElementsByClassName('queryCardGo')[0].addEventListener('click', function(){
    var queryCardType = document.querySelector('#queryCard select[name="queryCardType"]').value;
    if(queryCardType){
        populateLogs('Query Card requested type: ' + queryCardType);
        disableButtons();
        let request = {
            queryCardType: queryCardType
        };
        var padRequest = buildPadReqeuest();
        var posApiRequest = buildPosApiRequestBody();
        request = {...request, ...posApiRequest};
        if(Object.keys(padRequest).length > 0){
            request = {...request, purchaseAnalysisData: padRequest};
        }
        let body = JSON.stringify(request);
        post('queryCard', body);
        
    } else {
        alert('Please select Query Card Type.');
    }
});


document.getElementsByClassName('statusGo')[0].addEventListener('click', function(){
    var statusType = document.querySelector('#status select[name="statusType"]').value;
    if(statusType){
        populateLogs('Status requested type: ' + statusType);
        disableButtons();
        let request = {
            statusType: statusType
        };
        var posApiRequest = buildPosApiRequestBody();
        request = {...request, ...posApiRequest};
        let body = JSON.stringify(request);
        post('status', body);
    } else {
        alert('Please select Status');
    }
});

document.getElementsByClassName('configureMerchantGo')[0].addEventListener('click', function(){
    var catId = document.querySelector('#configureMerchant input[name="catId"]').value;
    var caId = document.querySelector('#configureMerchant input[name="caId"]').value;
    
    populateLogs('Status requested type: ');
    disableButtons();
    let request = {
        catId: catId,
        caId: caId
    };
    var posApiRequest = buildPosApiRequestBody();
    request = {...request, ...posApiRequest};
    let body = JSON.stringify(request);
    post('configureMerchant', body);
});


function buildPosApiRequestBody() {
    var merchant = document.querySelector('#header-inputs input[name="merchant"]').value;
    var receipt = document.querySelector('#header-inputs select[name="receipt"]').value;
    var cutReceipt = document.querySelector('#header-inputs input[name="cutReceipt"]').checked;
    
    return {merchant: merchant, receiptAutoPrint: receipt, cutReceipt: cutReceipt};
}

function buildPadReqeuest(){
    var pad = {};
        
    var padUse1 = document.querySelector('#pad-content input[name="use1"]').checked;
    var padUse2 = document.querySelector('#pad-content input[name="use2"]').checked;
    var padUse3 = document.querySelector('#pad-content input[name="use3"]').checked;
    var padUse4 = document.querySelector('#pad-content input[name="use4"]').checked;
    
    var tag1 = document.querySelector('#pad-content input[name="tag1"]').value;
    var value1 = document.querySelector('#pad-content input[name="value1"]').value;
    var tag2 = document.querySelector('#pad-content input[name="tag2"]').value;
    var value2 = document.querySelector('#pad-content input[name="value2"]').value;
    var tag3 = document.querySelector('#pad-content input[name="tag3"]').value;
    var value3 = document.querySelector('#pad-content input[name="value3"]').value;
    var tag4 = document.querySelector('#pad-content input[name="tag4"]').value;
    var value4 = document.querySelector('#pad-content input[name="value4"]').value;
    
    if(padUse1 && tag1 && value1){
        pad = {[tag1]: value1};
    }
    if(padUse2 && tag2 && value2){
        pad = {...pad, [tag2]: value2};
    }
    if(padUse3 && tag3 && value3){
        pad = {...pad, [tag3]: value3};
    }
    if(padUse4 && tag4 && value4){
        pad = {...pad, [tag4]: value4};
    }
    return pad;
}

document.getElementsByClassName('transactionGo')[0].addEventListener('click', function(){
    
    var transactionType = document.querySelector('#transaction select[name="transactionType"]').value;
    if('NotSet' === transactionType || 'Unknown' === transactionType){
        alert('Transaction type not supported.');
        return;
    }
    disableButtons();
    
    var txnRef = document.querySelector("#transaction input[name='txnRef']").value;
    var amount = document.querySelector("#transaction input[name='amount']").value;
    var amountCash = document.querySelector("#transaction input[name='amountCash']").value;
    var rfn = document.querySelector("#transaction input[name='rfn']").value;
    var pai = document.querySelector("#transaction input[name='pai']").value;
    
    // General
    var authCode = document.querySelector("#general input[name='authCode']").value;
    var rrn = document.querySelector("#general input[name='rrn']").value;
    var panSource = document.querySelector('#general select[name="panSource"]').value;
    var accountType = document.querySelector('#general select[name="accountType"]').value;
    var pan = document.querySelector("#general input[name='pan']").value;
    var dateExpiry = document.querySelector("#general input[name='dateExpiry']").value;
    var track2 = document.querySelector("#general input[name='track2']").value;
    var currencyCode = document.querySelector('#general select[name="currencyCode"]').value;
    var trainingMode = document.querySelector('#general input[name="traningMode"]').checked;
    var plb = document.querySelector('#general input[name="plb"]').checked;
    
    
    // tipping
    var tipOption = document.querySelector("#tipping input[name='options']:checked").value;
    var tipOptions1 = document.querySelector("#tipping input[name='tip-option1']").value;
    var tipOptions2 = document.querySelector("#tipping input[name='tip-option2']").value;
    var tipOptions3 = document.querySelector("#tipping input[name='tip-option3']").value;
    var fixedOptionAmount = document.querySelector("#tipping input[name='fixedAmount']").value;
    
    // surcharging
    var surchargeType1 = document.querySelector('#surcharging select[name="surchargeType1"]').value;
    var surchargeBin1 = document.querySelector('#surcharging input[name="surchargeBin1"]').value;
    var surchargeValue1 = document.querySelector('#surcharging input[name="surchargeValue1"]').value;
    var surchargeType2 = document.querySelector('#surcharging select[name="surchargeType2"]').value;
    var surchargeBin2 = document.querySelector('#surcharging input[name="surchargeBin2"]').value;
    var surchargeValue2 = document.querySelector('#surcharging input[name="surchargeValue2"]').value;
    
    let request = {
        txnType: transactionType,
        txnRef: txnRef,
        amount: amount,
        amountCash: amountCash,
        rfn: rfn,
        currencyCode: currencyCode,
        trainingMode: trainingMode,
        authCode: authCode,
        panSourceTemp: panSource,
        pan: pan,
        dateExpiry: dateExpiry,
        track2: track2,
        accountTypeTemp: accountType,
        rrn: rrn,
        tipOption: tipOption,
        tipOptions1: tipOptions1,
        tipOptions2: tipOptions2,
        tipOptions3: tipOptions3,
        fixedOptionAmount: fixedOptionAmount,
        pai: pai,
        surchargeType1: surchargeType1,
        surchargeType2: surchargeType2,
        surchargeBin1: surchargeBin1,
        surchargeBin2: surchargeBin2,
        surchargeValue1: surchargeValue1,
        surchargeValue2: surchargeValue2,
        plb: plb
    };
    var posApiRequest = buildPosApiRequestBody();
    request = {...request, ...posApiRequest};
    
    var padRequest = buildPadReqeuest();
    if(Object.keys(padRequest).length > 0){
        request = {...request, purchaseAnalysisData: padRequest};
    }
    post('transactionRequest', JSON.stringify(request));
    
});

document.getElementsByClassName('reprintReceiptGo')[0].addEventListener('click', function(){
    populateLogs('Reprint receipt requested.');
    disableButtons();
    let request = {};
    let body = JSON.stringify(request);
    post('reprintReceipt', body);
});

function disableButtons(){
    document.querySelectorAll('button.go, button.back').forEach(f => {
        f.disabled = true;
    });
    document.querySelectorAll('span.loading').forEach(f => {
        f.style.display = 'inline';
    });
};

function enableButtons(){
    document.querySelectorAll('button.go, button.back').forEach(f => {
        f.disabled = false;
    });
    document.querySelectorAll('span.loading').forEach(f => {
        f.style.display = 'none';
    });
};


function tipOption(evt, optionName) {
  var i;
  var tipOptions = document.getElementsByClassName("tip-options");
  for (i = 0; i < tipOptions.length; i++) {
    tipOptions[i].style.display = "none";
  }
  document.getElementById(optionName).style.display = "block";
}

function chooseProcess(evt, divId){
    enableButtons();
    document.getElementById('home').style.display = 'none';
    
    var divs = document.querySelectorAll('.process.inputs');
    for (i = 0; i < divs.length; i++) {
      divs[i].style.display = "none";
    }
    var processDiv = document.getElementById(divId);
    processDiv.style.display = 'block';
    var logDisplay = divId.replace(divId[0], divId[0].toUpperCase());
    populateLogs(logDisplay + ' Menu');
    
    if('TRANSACTION' === divId.toUpperCase()){
        txnRef();
    }
}

var backButtons = document.getElementsByClassName("back");
for (i = 0; i < backButtons.length; i++) {
  backButtons[i].addEventListener("click", displayHome);
}

function displayHome() {
  var divs = document.querySelectorAll('.process.inputs');
  for (i = 0; i < divs.length; i++) {
    divs[i].style.display = "none";
  }
  document.getElementById('home').style.display = 'block';
    
  document.querySelectorAll("span.advanced-settings.down")
    .forEach(s => {
        s.innerHTML  = '&#x290B; &nbsp;Advanced Settings';
        s.classList.add('up');
        s.classList.remove('down');
    });
}

function toggleAdvancedSettings(event, process){
    var divPanel = document.querySelector("#" + process + " .advance-settings-panel");
    var span =  document.querySelector("#" + process + " span.advanced-settings");
    var advancedSettingsDiv = document.getElementById("advanced-settings");

    advancedSettingsDiv.querySelectorAll('.tab button')
        .forEach(btn => btn.remove());
    
    var tabDiv = advancedSettingsDiv.querySelector('.tab');
    if(process === 'transaction'){
        document.querySelector("#advanced-settings #general").style.display = 'block';
        document.querySelector("#advanced-settings #pad-content").style.display = 'none';
        
        var generalBtn = createButton('general', 'General');
        generalBtn.classList.add('active');
        tabDiv.appendChild(generalBtn);
        tabDiv.appendChild(createButton('tipping', 'Tipping'));
        tabDiv.appendChild(createButton('surcharging', 'Surcharging'));
    } else {
        document.querySelector("#advanced-settings #pad-content").style.display = 'block';
        document.querySelector("#advanced-settings #general").style.display = 'none';
        document.querySelector("#advanced-settings #surcharging").style.display = 'none';
        document.querySelector("#advanced-settings #tipping").style.display = 'none';
    }
    
    tabDiv.appendChild(createButton('pad-content', 'Purchase Analysis Data'));
    if(span.getAttribute('class').includes('up')){
        span.innerHTML  = '&#x290A; &nbsp;Advanced Settings';
        span.classList.remove('up');
        span.classList.add('down');
        advancedSettingsDiv.style.display = 'block';
        divPanel.appendChild(advancedSettingsDiv);
    } else {
        var tempDiv = document.getElementById("temp-div");
        span.innerHTML  = '&#x290B; &nbsp;Advanced Settings';
        span.classList.add('up');
        span.classList.remove('down');
        tempDiv.appendChild(advancedSettingsDiv);
        advancedSettingsDiv.style.display = 'none';
        divPanel.replaceChildren();
    }
}

function createButton(tag, name){
    var btn = document.createElement('button');
    btn.textContent = name;
    btn.classList.add('tablinks');
    btn.setAttribute('data-tag', tag);
    
    btn.addEventListener('click', function openTransactionTab(evt) {
      var i;
      var tabcontent = document.getElementsByClassName("tabcontent");
      for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
      }
      var tablinks = document.getElementsByClassName("tablinks");
      for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
      }
      var tag = this.getAttribute('data-tag');
      document.getElementById(tag).style.display = "block";
      evt.currentTarget.className += " active";
    });
    return btn;
}

function toggleResultReciept(evt, tag) {
  var i, tabcontent, tablinks;
  tabcontent = document.getElementsByClassName("tabcontent-result-receipt");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }
  tablinks = document.getElementsByClassName("tablinks-result-receipt");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].className = tablinks[i].className.replace(" active", "");
  }
  document.getElementById(tag).style.display = "block";
  evt.currentTarget.className += " active";
}

function populateResult(body){
    var content = '<table>';
    Object.keys(body).forEach(function(key) {
        var val = JSON.stringify(body[key]);
        key = key.toUpperCase();
        if('TXNFLAGS' === key || 'OPTIONFLAGS' === key || 'DISPLAYTEXT' === key){
            var parsedVal = '';
            var arrayVal = JSON.parse(val);
            Object.keys(arrayVal).forEach(function(subKey) {
                var subVal = JSON.stringify(arrayVal[subKey]);
                subKey = subKey[0].toUpperCase() + subKey.substring(1);
                parsedVal += subKey + ': ' + subVal + '<br />';
            });
            val = parsedVal;
        } else {
            if(val === null || val === "{}" || val === 'null'){
                val = "";
            }
            if(val.charAt(0) === '\"' && val.at(-1) === '\"'){
                val = JSON.parse(val);
            }
            if(val == null){
                val = "";
            }
        }
        key = key[0].toUpperCase() + key.substring(1);
        content += '<tr>';
        content += '<td>' + key + '</td>';
        content += '<td>' + val + '</td>';
        content += '</tr>';
    });
    content += '</table>';
    document.getElementById('result').innerHTML = content;
}

function populateLogs(text){
    var logDiv = document.getElementById('logs');
    logDiv.innerHTML += '<br />' + getDate() + ' ' + text;          
}

function getDate(){
    let now = new Date();
    let day = now.getDate();
    let month = now.getMonth();
    let year = now.getFullYear();
    var hours = now.getHours();
    var minutes = now.getMinutes();
    var milli = now.getMilliseconds();
    var seconds = now.getSeconds();
    var ampm = hours >= 12 ? 'PM' : 'AM';
    
    if(day < 10){
        day = '0' + day;
    }
    if(month < 10){
        month = '0' + month;
    }
    if(hours === 0){
        hours = '12';
    }
    
    return day + '/' + month + '/' + year + ' ' + hours 
        + ':' + minutes + ':' + seconds + '.' + milli + ' ' +ampm;
}

function post(endpoint, body) {
    const url = "http://localhost:8080/" + endpoint;
    let xhr = new XMLHttpRequest()
    xhr.open('POST', url, true)
    xhr.setRequestHeader('Content-type', 'application/json; charset=UTF-8')
    xhr.send(body);
    xhr.onload = function () {
        if(xhr.status !== 200) {
            enableButtons();
        }
        if(xhr.status == 200 && xhr.responseText.length > 2){
            populateLogs('Session Id: ' + xhr.responseText);
        }
    }
}
function refreshSessionOptions(uuid){
    let newOption = new Option(uuid, uuid);
    var select = document.querySelector('#session select[name="sessionId"]');
    select.add(newOption, undefined);
}

function txnRef(){
    var digits = 10;
    var rnd = '';
    for(let i = 0; i < digits; i++){
        rnd += Math.floor(0 + Math.random()* 9 - 0);
    }
    document.querySelector('#transaction input[name="txnRef"]').value = 'LNK' + rnd;
}

var lastTransactionInterrupted = document.querySelector('input[name="lastTransactionInterrupted"]');
window.addEventListener('load', function () {
    txnRef();
    if(lastTransactionInterrupted && lastTransactionInterrupted.value) {
        let text = "Last request was interrupted. Do you want to retrieve last result?";
        window.setTimeout(function(){
            if (confirm(text) == true) {
                chooseProcess(null, 'session');
                var doc = document.querySelector('#session .sessionGo');
                doc.click();
            } else {
                console.log('canceled');
            }
        }, 300);
    }
});

function sendKey(evt) {
    var btn = document.querySelector('.approveBox button')
    var sessionId = btn.getAttribute('data-sessionId');
    var key = btn.getAttribute('data-key');
    let request = {
        sessionId: sessionId,
        key: key,
        data: ""
    };
    let body = JSON.stringify(request);
    post('sendKey', body);
    document.querySelector('.approveBox').style.display = "none";
}

function displayTransactionInputs(rfn, amount, amountCash, totalCheques, pai){
  document.querySelector('#transaction .rfn').style.display = rfn;
  document.querySelector('#transaction .amount').style.display = amount;
  document.querySelector('#transaction .amountCash').style.display = amountCash;
  document.querySelector('#transaction .totalCheques').style.display = totalCheques;
  document.querySelector('#transaction .pai').style.display = pai;  
}

document.querySelector("#transaction select[name='transactionType']")
    .onchange=function (e){
  var selectedOption = e.target.value;
  var block = 'table-row';
  var none = 'none';
  
  if('Purchase' === selectedOption){
    displayTransactionInputs(none, block, block, none, none);  
  }
  else if('Cash' === selectedOption){
    displayTransactionInputs(none, none, block, none, none);
  }
  else if('Refund' === selectedOption){
    displayTransactionInputs(block, block, none, none, none);
  }
  else if('Deposit' === selectedOption){
    displayTransactionInputs(none, block, block, block, none);
  }
  else if('PreAuth' === selectedOption){
    displayTransactionInputs(none, block, none, none, none);
  }
  else if('PreAuthExtend' === selectedOption){
    displayTransactionInputs(block, none, none, none, none);
  }
  else if('PreAuthTopUp' === selectedOption){
    displayTransactionInputs(block, block, none, none, none);
  }
  else if('PreAuthCancel' === selectedOption){
    displayTransactionInputs(block, none, none, none, none);
  }
  else if('PreAuthPartialCancel' === selectedOption){
    displayTransactionInputs(block, block, none, none, none);
  }
  else if('PreAuthComplete' === selectedOption){
    displayTransactionInputs(block, block, none, none, none);
  }
  else if('PreAuthInquiry' === selectedOption){
    displayTransactionInputs(block, none, none, none, block);
  }
  else if('Void' === selectedOption){
    displayTransactionInputs(none, block, none, none, none);
  }
  else if('NotSet' === selectedOption){
    displayTransactionInputs(none, none, none, none, none);  
  }
  else if('Unknown' === selectedOption){
      displayTransactionInputs(none, none, none, none, none);
  }
}

function clearLogs(event){
    document.getElementById('logs').innerHTML = '';
}

document.querySelector("#header-inputs select[name='users']")
    .onchange=function (e){
    var username = e.target.value;
    changeLane(username);
};

function changeLane(username){
    const url = "http://localhost:8080/change-lane/" + username;
    let xhr = new XMLHttpRequest()
    xhr.open('PUT', url, true)
    xhr.setRequestHeader('Content-type', 'application/json; charset=UTF-8')
    xhr.send(null);
    xhr.onload = function () {
        var sessionIds = document.querySelector("#session select[name='sessionId']");
        Array.from(sessionIds).forEach((option) => {
          sessionIds.removeChild(option);
        });
        JSON.parse(xhr['responseText']).forEach(function(sessionId) {
            var newOpt = document.createElement('option');
            newOpt.appendChild(document.createTextNode(sessionId));
            newOpt.value = sessionId;
            sessionIds.appendChild(newOpt);
        });
        document.querySelector("#session input[name='username']").value = username;
        populateLogs('Client Id:: ' + username);
    }
}


function saveLogs(event) {
  var element = document.createElement('a');
  
  let content = document.getElementById('logs').innerHTML;
  content = content.replaceAll('<br>', '');
  content = content.replaceAll('          ', '');
  content = content.replaceAll('&nbsp;', ' ');
  
  element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(content));
  element.setAttribute('download', "activity.log"); 
  element.style.display = 'none';
  document.body.appendChild(element);

  element.click();
  document.body.removeChild(element); 
}