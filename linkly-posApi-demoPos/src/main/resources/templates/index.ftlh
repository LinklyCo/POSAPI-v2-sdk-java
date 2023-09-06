<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" href="css/styles.css">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
  </head>
  <body>
  	<#if lastTransactionInterrupted??>
  		<input type="hidden" name="lastTransactionInterrupted" value="${lastTransactionInterrupted}" />
  	</#if>
    <div id="content">
		<div class="page-column">
		  <div id="home">
			<div id="header-inputs" class="inputs">
			  <table>
				<tr>
				  <td>Username:</td>
				  <td>
				  	<select name="users">
				  	  <#if users?size gt 0>
				        <#list users as user>
				          <option value="${user}" <#if currentUser == user>selected</#if>>${user}</option>
				        </#list>
				  	  </#if>
				   </select>
				  </td>
				  <td>Merchant:</td>
				  <td>
					<input type="text" name="merchant" value="00" class="merchantInput"/>
				  </td>
				</tr>
				<tr>
				  <td>Receipt </td>
				  <td>
					<select name="receipt">
					  <#list receiptAutoPrints as receiptAutoPrint>
					    <option value="${receiptAutoPrint}">${receiptAutoPrint}</option>
					   </#list>
					</select>
				  </td>
				  <td colspan="2">
					<input type="checkbox" name="cutReceipt" /> Cut Receipt
				  </td>
				</tr>
			  </table>
			</div>
			<table id="buttons">
			  <tr>
				<td onclick="chooseProcess(event, 'pairing')">Pairing</td>
				<td onclick="chooseProcess(event, 'transaction')">Transaction</td>
			  </tr>
			  <tr>
				<td onclick="chooseProcess(event, 'logon')">Logon</td>
				<td onclick="chooseProcess(event, 'status')">Status</td>
			  </tr>
			  <tr>
				<td onclick="chooseProcess(event, 'settlement')">Settlement</td>
				<td onclick="chooseProcess(event, 'reprintReceipt')">Reprint Receipt</td>
			  </tr>
			  <tr>
				<td onclick="chooseProcess(event, 'queryCard')">Query Card</td>
				<td onclick="chooseProcess(event, 'configureMerchant')">Configure Merchant</td>
			  </tr>
			  <tr>
				<td onclick="chooseProcess(event, 'session')">Session</td>
			  </tr>
			</table>
		  </div>
		  <div id="transaction" class="process inputs">
			<table>
			  <tr>
				<td>
				  <button type="submit" class="back">Back</button>
				</td>
			  </tr>
			  <tr>
				<td colspan="2">
				  <p>
					<b>Sale:</b>
				  </p>
				</td>
			  </tr>
			  <tr>
				<td>Transaction Type:</td>
				<td>
				  <select name="transactionType">
				    <#list txnTypes as txnType>
					  <option value="${txnType}">${txnType}</option>
					</#list>
				  </select>
				</td>
			  </tr>
			  <tr>
				<td>Txn Ref:</td>
				<td>
				  <input type="text" name="txnRef" />
				</td>
			  </tr>
			  <tr class="rfn">
				<td>RFN:</td>
				<td>
				  <input type="text" name="rfn" />
				</td>
			  </tr>
			  <tr class="amount">
				<td>Amount:</td>
				<td>
				  <input type="text" name="amount" />
				</td>
			  </tr>
			  <tr class="amountCash">
				<td>Amount Cash:</td>
				<td>
				  <input type="text" name="amountCash" />
				</td>
			  </tr>
			  <tr class="totalCheques">
				<td>Total Cheques:</td>
				<td>
				  <input type="text" name="totalCheques" onkeyup="value=value.replace(/[^\d]/g,'')"/>
				</td>
			  </tr>
			  <tr class="pai">
				<td>PAI:</td>
				<td>
				  <input type="text" name="pai" />
				</td>
			  </tr>
			  <tr>
				<td colspan="2">
				  <span class="advanced-settings up"  onclick="toggleAdvancedSettings(event, 'transaction')">&#x290B; &nbsp;Advanced Settings</span>
				</td>
			  </tr>
			</table>
			<div class="advance-settings-panel"></div>
			<br />
			<button type="submit" class="go transactionGo">Go</button>
			<span class="loading">Please wait...</span>
		  </div>
		  <div id="configureMerchant" class="process inputs">
			<table>
			  <tr>
				<td>
				  <button type="submit" class="back">Back</button>
				</td>
			  </tr>
			  <tr>
				<td colspan="2">
				  <p>
					<b>Configure</b>
				  </p>
				</td>
			  </tr>
			  <tr>
				<td>CatId:</td>
				<td>
				  <input type="text" name="catId" />
				</td>
			  </tr>
			  <tr>
				<td>CaId:</td>
				<td>
				  <input type="text" name="caId" />
				</td>
			  </tr>
			</table>
			<button type="submit" class="go configureMerchantGo">Go</button>
			<span class="loading">Please wait...</span>
		  </div>
		  <div id="reprintReceipt" class="process inputs">
			<table>
			  <tr>
				<td>
				  <button type="submit" class="back">Back</button>
				</td>
			  </tr>
			  <tr>
				<td colspan="2">
				  <p>
					<b>Reprint Receipt</b>
				  </p>
				</td>
			  </tr>
			</table>
			<button type="submit" class="go reprintReceiptGo">Go</button>
			<span class="loading">Please wait...</span>
		  </div>
		  <div id="status" class="process inputs">
			<table>
			  <tr>
				<td>
				  <button type="submit" class="back">Back</button>
				</td>
			  </tr>
			  <tr>
				<td colspan="2">
				  <p>
					<b>Select a status type:</b>
				  </p>
				</td>
			  </tr>
			  <tr>
				<td>Status Type:</td>
				<td>
				  <select name="statusType">
				    <#list statusTypes as statusType>
					  <option value="${statusType}">${statusType}</option>
					</#list>
				  </select>
				</td>
			  </tr>
			</table>
			<button type="submit" class="go statusGo">Go</button>
			<span class="loading">Please wait...</span>
		  </div>
		  <div id="session" class="process inputs">
			<table>
			  <tr>
				<td>
				  <button type="submit" class="back">Back</button>
				</td>
			  </tr>
			  <tr>
				<td colspan="2">
				  <p>
					<b>Retrieve events from previous sessions</b>
				  </p>
				</td>
			  </tr>
			  <tr>
				<td>Username:</td>
				<td>
				  <input type="text" name="username" value="${currentUser}" disabled/>
				</td>
			  </tr>
			  <tr>
				<td>Session Id:</td>
				<td>
				  <select name="sessionId">
				  	 <#if sessions?size gt 0>
				        <#list sessions as session>
				          <#if lastTransactionInterrupted?? && lastTransactionInterrupted?has_content 
				          		&& session == lastTransactionInterrupted>
				          	<option value="${session}" selected>${session}</option>
				          <#else>
				         	 <option value="${session}">${session}</option>
				          </#if>
				        </#list>
				  	  </#if>
				  </select>
				</td>
			  </tr>
			</table>
			<br />
			<button type="submit" class="go sessionGo">Get Events</button>
			<span class="loading">Please wait...</span>
		  </div>
		  <div id="queryCard" class="process inputs">
			<table>
			  <tr>
				<td>
				  <button type="submit" class="back">Back</button>
				</td>
			  </tr>
			  <tr>
				<td colspan="2">
				  <p>
					<b>Select a Query Type</b>
				  </p>
				</td>
			  </tr>
			  <tr>
				<td>Query Type:</td>
				<td>
				  <select name="queryCardType">
				    <#list queryCardTypes as queryCardType>
					  <option value="${queryCardType}">${queryCardType}</option>
					</#list>
				  </select>
				</td>
			  </tr>
			  <tr>
				<td colspan="2">
				  <span class="advanced-settings up" onclick="toggleAdvancedSettings(event, 'queryCard')">&#x290B; &nbsp;Advanced Settings</span>
				</td>
			  </tr>
			</table>
			<div class="advance-settings-panel"></div>
			<button type="submit" class="go queryCardGo">Go</button>
			<span class="loading">Please wait...</span>
		  </div>
		  <div id="settlement" class="process inputs">
			<table>
			  <tr>
				<td>
				  <button type="submit" class="back">Back</button>
				</td>
			  </tr>
			  <tr>
				<td colspan="2">
				  <p>
					<b>Select a Settlement Type</b>
				  </p>
				</td>
			  </tr>
			  <tr>
				<td>Settlement Type:</td>
				<td>
				   <select name="settlementType">
				      <#list settlementTypes as settlementType>
					    <option value="${settlementType}">${settlementType}</option>
					  </#list>
					</select>
				</td>
			  </tr>
			  <tr>
				<td colspan="2">
				  <span class="advanced-settings up" onclick="toggleAdvancedSettings(event, 'settlement')">&#x290B; &nbsp;Advanced Settings</span>
				</td>
			  </tr>
			</table>
			<div class="advance-settings-panel"></div>
			<button type="submit" class="go settlementGo">Go</button>
			<span class="loading">Please wait...</span>
		  </div>
		  <div id="logon" class="process inputs">
			<table>
			  <tr>
				<td>
				  <button type="submit" class="back">Back</button>
				</td>
			  </tr>
			  <tr>
				<td colspan="2">
				  <p>
					<b>Select a Logon Type</b>
				  </p>
				</td>
			  </tr>
			  <tr>
				<td>Logon Type:</td>
				<td>
				  <select name="logonType">
					<#list logonTypes as logonType>
				      <option value="${logonType}">${logonType}</option>
				    </#list>
				  </select>
				</td>
			  </tr>
			  <tr>
				<td colspan="2">
				  <span class="advanced-settings up" onclick="toggleAdvancedSettings(event, 'logon')">&#x290B; &nbsp;Advanced Settings</span>
				</td>
			  </tr>
			</table>
			<div class="advance-settings-panel"></div>
			<button type="submit" class="go logonGo">Go</button>
			<span class="loading">Please wait...</span>
		  </div>
		  <div id="pairing" class="process inputs">
			<table>
			  <tr>
				<td>
				  <button type="submit" class="back">Back</button>
				</td>
			  </tr>
			  <tr>
				<td colspan="2">
				  <p>
					<b>Please provide the following to connect to Linkly Cloud:</b>
				  </p>
				</td>
			  </tr>
			  <tr>
				<td>Authentication URL:</td>
				<td>
				  <input type="text" name="authUrl" value="${endpoints[0]}" required/>
				</td>
			  </tr>
			  <tr>
				<td>POS URL:</td>
				<td>
				  <input type="text" name="posUrl"  value="${endpoints[0]}" required/>
				</td>
			  </tr>
			  <tr>
				<td>* Username:</td>
				<td>
				  <input type="text" name="username"/>
				</td>
			  </tr>
			  <tr>
				<td>* Password:</td>
				<td>
				  <input type="password" name="password" />
				</td>
			  </tr>
			  <tr>
				<td>* Pinpad Pairing Code:</td>
				<td>
				  <input type="text" name="pairCode"/>
				</td>
			  </tr>
			</table>
			<button type="submit" class="go pairingGo">Go</button>
			<span class="loading">Please wait...</span>
		  </div>
		  <div class="approveBox">
			<p></p>
			<button type="submit" class="approveCancel" onclick="sendKey(event)" data-key=""></button>
		  </div>
		</div>
		<div class="page-column divider">
		  <div class="tab-result-receipt">
			<button class="tablinks-result-receipt active" onclick="toggleResultReciept(event, 'result')">Result</button>
			<button class="tablinks-result-receipt" onclick="toggleResultReciept(event, 'receipt')">Receipt</button>
		  </div>
		  <div id="result" class="tabcontent-result-receipt">
			Result
		  </div>
		  <div id="receipt" class="tabcontent-result-receipt">
		    Receipt
		  </div>
		</div>
		<div class="page-column divider"> 
		  <span>Activity</span>
		  <table align="right">
			<tr>
				<td><input type="checkbox" name="trace" />&nbsp;Trace&nbsp;&nbsp;</td>
				<td><button type="submit" name="save" onclick="saveLogs(event)">Save</button>&nbsp;&nbsp;</td>
				<td><button type="submit" name="clear" onclick="clearLogs(event)">Clear</button>&nbsp;&nbsp;</td>
			</tr>
		  </table>
		  <br />
		  <br />
		  <hr />
		  <div id="logs">
		  ${.now?string["dd/MM/yyyy hh:mm.sss a"]} Application started. Version ${posVersion}
		  <#if users?size gt 0>
		  <br />
		  ${.now?string["dd/MM/yyyy hh:mm.sss a"]} Client Id: ${users[0]}
		  </#if>
		  </div>
		</div>
    </div>
	<div id="temp-div">
		<div id="advanced-settings">
		  <br />
		  <div class="tab"></div>
		  <div id="general" class="tabcontent">
			<table>
			  <tr>
				<td>Auth No:</td>
				<td>
				  <input type="text" name="authCode" value="0" value=value.replace(/[^\d]/g,'')/>
				</td>
			  </tr>
			  <tr>
				<td>RRN:</td>
				<td>
				  <input type="text" name="rrn" value=""/>
				</td>
			  </tr>
			  <tr>
				<td>PAN Source:</td>
				<td>
				  <select name="panSource">
					<option dsiabled></option>
					<#list panSources as panSource>
				      <option value="${panSource}">${panSource}</option>
				    </#list>
				  </select>
				</td>
			  </tr>
			  <tr>
				<td>Account Type:</td>
				<td>
				  <select name="accountType">
					<option dsiabled></option>
					<#list accountTypes as accountType>
				      <option value="${accountType}">${accountType}</option>
				    </#list>
				  </select>
				</td>
			  </tr>
			  <tr>
				<td>PAN:</td>
				<td>
				  <input type="text" name="pan" />
				</td>
			  </tr>
			  <tr>
				<td>Expiry:</td>
				<td>
				  <input type="text" name="dateExpiry" />
				</td>
			  </tr>
			  <tr>
				<td>Track2:</td>
				<td>
				  <input type="text" name="track2" />
				</td>
			  </tr>
			  <tr>
				<td>Currency Code:</td>
				<td>
				  <select name="currencyCode">
					<option>AUD</option>
					<option>NZD</option>
				  </select>
				</td>
			  </tr>
			  <tr>
				<td>Original Txn Type:</td>
				<td>
				  <select name="txnType">
					<option dsiabled></option>
					<#list txnTypes as txnType>
				      <option value="${txnType}">${txnType}</option>
				    </#list>
				  </select>
				</td>
			  </tr>
			  <tr>
				<td>
				  <input type="checkbox" name="traningMode" /> Training Mode
				</td>
				<td>
				  <input type="checkbox" name="plb" /> Product Level Blocking
				</td>
			  </tr>
			</table>
		  </div>
		  <div id="tipping" class="tabcontent">
			<p>Options</p>
			<input type="radio" id="option-none" name="options" value="none" checked>
			<label for="option-none">None</label>
			<br>
			<input type="radio" id="option-pinpad" name="options" value="pinpad">
			<label for="option-pinpad">Use pinpad tipping</label>
			<br>
			<input onclick="tipOption(event, 'tip-option')" type="radio" id="option-tip-option" name="options" value="tipOptions">
			<label onclick="tipOption(event, 'tip-option')" for="option-tip-option">Tip with options</label>
			<br>
			<input onclick="tipOption(event, 'tip-fixed')" type="radio" id="option-fixed" name="options" value="tipFixed">
			<label onclick="tipOption(event, 'tip-fixed')" for="option-fixed">Tip with fixed amount</label>
			<br>
			<br />
			<div id="tip-option" class="tip-options"> Options:
			  <input type="text" name="tip-option1" value="" onkeyup="value=value.replace(/[^\d]/g,'')"/>
			  <input type="text" name="tip-option2" value="" onkeyup="value=value.replace(/[^\d]/g,'')"/>
			  <input type="text" name="tip-option3" value="" onkeyup="value=value.replace(/[^\d]/g,'')"/>
			</div>
			<div id="tip-fixed" class="tip-options"> Amount: <input type="text" name="fixedAmount"
				value="" onkeyup="value=value.replace(/[^\d]/g,'')"/>
			</div>
		  </div>
		  <div id="surcharging" class="tabcontent">
			<table>
			  <tr>
				<td>Type</td>
				<td>Bin</td>
				<td>Value</td>
			  </tr>
			  <#list surchargeRates as rate>
			  	<tr>
				  <td>
				    <select name="surchargeType${rate?index + 1}">
					  <option value="Fixed" <#if rate.type == "Fixed">selected</#if>>Fixed</option>
					  <option value="Percentage" <#if rate.type == "Percentage">selected</#if>>Percentage</option>
				    </select>
				  </td>
				  <td><input type="text" name="surchargeBin${rate?index + 1}" value="${rate.bin}"
				  	onkeyup="value=value.replace(/[^\d]/g,'')"/></td>
				  <td><input type="text" name="surchargeValue${rate?index + 1}" value="${rate.value}"
				  	onkeyup="value=value.replace(/[^\d]/g,'')"/></td>
			    </tr>
			  </#list>
			</table>
			<br />
		  </div>
		  <div id="pad-content" class="tabcontent">
			<table>
			  <tr>
				<td>Use</td>
				<td>Tag</td>
				<td>Value</td>
			  </tr>
			  <#list pads as pad>
				  <tr>
					<td>
					  <input type="checkbox" name="use${pad?index + 1}" <#if pad.use>checked</#if>/>
					</td>
					<td>
					  <input type="text" name="tag${pad?index + 1}" value="${pad.tag}"/>
					</td>
					<td>
					  <input type="text" name="value${pad?index + 1}"  value="${pad.value}"/>
					</td>
				  </tr>
			  </#list>
			</table>
		  </div>
		</div>
	</div>
    <script src="js/scripts.js?v=1.1"></script>
  </body>
</html>