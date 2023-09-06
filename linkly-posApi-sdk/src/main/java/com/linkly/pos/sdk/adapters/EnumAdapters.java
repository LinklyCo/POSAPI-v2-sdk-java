package com.linkly.pos.sdk.adapters;

import com.linkly.pos.sdk.common.EnumLookup;
import com.linkly.pos.sdk.models.enums.AccountType;
import com.linkly.pos.sdk.models.enums.CardEntryType;
import com.linkly.pos.sdk.models.enums.CommsMethodType;
import com.linkly.pos.sdk.models.enums.CurrencyStatus;
import com.linkly.pos.sdk.models.enums.EftTerminalType;
import com.linkly.pos.sdk.models.enums.GraphicCode;
import com.linkly.pos.sdk.models.enums.InputType;
import com.linkly.pos.sdk.models.enums.KeyHandlingType;
import com.linkly.pos.sdk.models.enums.LogonType;
import com.linkly.pos.sdk.models.enums.NetworkType;
import com.linkly.pos.sdk.models.enums.PanSource;
import com.linkly.pos.sdk.models.enums.PayPassStatus;
import com.linkly.pos.sdk.models.enums.QueryCardType;
import com.linkly.pos.sdk.models.enums.ReceiptAutoPrint;
import com.linkly.pos.sdk.models.enums.ReceiptType;
import com.linkly.pos.sdk.models.enums.ReprintType;
import com.linkly.pos.sdk.models.enums.ResponseType;
import com.linkly.pos.sdk.models.enums.SettlementType;
import com.linkly.pos.sdk.models.enums.StatusType;
import com.linkly.pos.sdk.models.enums.TerminalCommsType;
import com.linkly.pos.sdk.models.enums.TxnType;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

/**
 * Collection of Adapters that converts project enums to json string representation and vice versa.
 */
public class EnumAdapters {

	/**
	 * Converts AccountType enums to json string representation and vice versa.
	 */
    public static class AccountTypeAdapter {
    	
        /**
        * Converts AccountType to json string representation.
        * 
        * @param value AccountType value to be converted.
        */
        @ToJson
        public String toJson(AccountType value) {
            return value.getValue();
        }

        /**
        * Converts json AccountType string representation to AccountType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public AccountType fromJson(String value) {
            return EnumLookup.findEnumValue(AccountType.class, value);
        }
    }

	/**
	 * Converts CardEntryType enums to json string representation and vice versa.
	 */
    public static class CardEntryTypeAdapter {
    	
        /**
        * Converts CardEntryType to json string representation.
        * 
        * @param value CardEntryType value to be converted.
        */
        @ToJson
        public String toJson(CardEntryType value) {
            return value.getValue();
        }

        /**
        * Converts json CardEntryType string representation to CardEntryType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public CardEntryType fromJson(String value) {
            return EnumLookup.findEnumValue(CardEntryType.class, value);
        }
    }

	/**
	 * Converts CommsMethodType enums to json string representation and vice versa.
	 */
    public static class CommsMethodTypeAdapter {
    	
        /**
        * Converts CommsMethodType to json string representation.
        * 
        * @param value CommsMethodType value to be converted.
        */
        @ToJson
        public String toJson(CommsMethodType value) {
            return value.getValue();
        }

        /**
        * Converts json CommsMethodType string representation to CommsMethodType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public CommsMethodType fromJson(String value) {
            return EnumLookup.findEnumValue(CommsMethodType.class, value);
        }
    }

	/**
	 * Converts CurrencyStatus enums to json string representation and vice versa.
	 */
    public static class CurrencyStatusAdapter {
    	
        /**
        * Converts CurrencyStatus to json string representation.
        * 
        * @param value CurrencyStatus value to be converted.
        */
        @ToJson
        public String toJson(CurrencyStatus value) {
            return value.getValue();
        }

        /**
        * Converts json CurrencyStatus string representation to CurrencyStatus enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public CurrencyStatus fromJson(String value) {
            return EnumLookup.findEnumValue(CurrencyStatus.class, value);
        }
    }

	/**
	 * Converts EftTerminalType enums to json string representation and vice versa.
	 */
    public static class EftTerminalTypeAdapter {
    	
        /**
        * Converts EftTerminalType to json string representation.
        * 
        * @param value EftTerminalType value to be converted.
        */
        @ToJson
        public String toJson(EftTerminalType value) {
            return value.getValue();
        }

        /**
        * Converts json EftTerminalType string representation to EftTerminalType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public EftTerminalType fromJson(String value) {
            return EnumLookup.findEnumValue(EftTerminalType.class, value);
        }
    }

	/**
	 * Converts GraphicCode enums to json string representation and vice versa.
	 */
    public static class GraphicCodeAdapter {
    	
        /**
        * Converts GraphicCode to json string representation.
        * 
        * @param value GraphicCode value to be converted.
        */
        @ToJson
        public String toJson(GraphicCode value) {
            return value.getValue();
        }

        /**
        * Converts json GraphicCode string representation to GraphicCode enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public GraphicCode fromJson(String value) {
            return EnumLookup.findEnumValue(GraphicCode.class, value);
        }
    }

	/**
	 * Converts InputType enums to json string representation and vice versa.
	 */
    public static class InputTypeAdapter {
    	
        /**
        * Converts InputType to json string representation.
        * 
        * @param value InputType value to be converted.
        */
        @ToJson
        public String toJson(InputType value) {
            return value.getValue();
        }

        /**
        * Converts json InputType string representation to InputType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public InputType fromJson(String value) {
            return EnumLookup.findEnumValue(InputType.class, value);
        }
    }

	/**
	 * Converts KeyHandlingType enums to json string representation and vice versa.
	 */
    public static class KeyHandlingTypeAdapter {
    	
        /**
        * Converts KeyHandlingType to json string representation.
        * 
        * @param value KeyHandlingType value to be converted.
        */
        @ToJson
        public String toJson(KeyHandlingType value) {
            return value.getValue();
        }

        /**
        * Converts json KeyHandlingType string representation to KeyHandlingType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public KeyHandlingType fromJson(String value) {
            return EnumLookup.findEnumValue(KeyHandlingType.class, value);
        }
    }

	/**
	 * Converts LogonType enums to json string representation and vice versa.
	 */
    public static class LogonTypeAdapter {
    	
        /**
        * Converts LogonType to json string representation.
        * 
        * @param value LogonType value to be converted.
        */
        @ToJson
        public String toJson(LogonType value) {
            return value.getValue();
        }

        /**
        * Converts json LogonType string representation to LogonType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public LogonType fromJson(String value) {
            return EnumLookup.findEnumValue(LogonType.class, value);
        }
    }

	/**
	 * Converts NetworkType enums to json string representation and vice versa.
	 */
    public static class NetworkTypeAdapter {
    	
        /**
        * Converts NetworkType to json string representation.
        * 
        * @param value NetworkType value to be converted.
        */
        @ToJson
        public String toJson(NetworkType value) {
            return value.getValue();
        }

        /**
        * Converts json NetworkType string representation to NetworkType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public NetworkType fromJson(String value) {
            return EnumLookup.findEnumValue(NetworkType.class, value, NetworkType.Unknown.getValue());
        }
    }

	/**
	 * Converts PanSource enums to json string representation and vice versa.
	 */
    public static class PanSourceAdapter {
    	
        /**
        * Converts PanSource to json string representation.
        * 
        * @param value PanSource value to be converted.
        */
        @ToJson
        public String toJson(PanSource value) {
            return value.getValue();
        }

        /**
        * Converts json PanSource string representation to PanSource enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public PanSource fromJson(String value) {
            return EnumLookup.findEnumValue(PanSource.class, value);
        }
    }

	/**
	 * Converts PayPassStatus enums to json string representation and vice versa.
	 */
    public static class PayPassStatusAdapter {
    	
        /**
        * Converts PayPassStatus to json string representation.
        * 
        * @param value PayPassStatus value to be converted.
        */
        @ToJson
        public String toJson(PayPassStatus value) {
            return value.getValue();
        }

        /**
        * Converts json PayPassStatus string representation to PayPassStatus enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public PayPassStatus fromJson(String value) {
            return EnumLookup.findEnumValue(PayPassStatus.class, value, PayPassStatus.Unknown.getValue());
        }
    }

	/**
	 * Converts QueryCardType enums to json string representation and vice versa.
	 */
    public static class QueryCardTypeAdapter {
    	
        /**
        * Converts QueryCardType to json string representation.
        * 
        * @param value QueryCardType value to be converted.
        */
        @ToJson
        public String toJson(QueryCardType value) {
            return value.getValue();
        }

        /**
        * Converts json QueryCardType string representation to QueryCardType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public QueryCardType fromJson(String value) {
            return EnumLookup.findEnumValue(QueryCardType.class, value);
        }
    }

	/**
	 * Converts ReceiptAutoPrint enums to json string representation and vice versa.
	 */
    public static class ReceiptAutoPrintAdapter {
    	
        /**
        * Converts ReceiptAutoPrint to json string representation.
        * 
        * @param value ReceiptAutoPrint value to be converted.
        */
        @ToJson
        public String toJson(ReceiptAutoPrint value) {
            return value.getValue();
        }

        /**
        * Converts json ReceiptAutoPrint string representation to ReceiptAutoPrint enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public ReceiptAutoPrint fromJson(String value) {
            return EnumLookup.findEnumValue(ReceiptAutoPrint.class, value);
        }
    }

	/**
	 * Converts ReceiptType enums to json string representation and vice versa.
	 */
    public static class ReceiptTypeAdapter {
    	
        /**
        * Converts ReceiptType to json string representation.
        * 
        * @param value ReceiptType value to be converted.
        */
        @ToJson
        public String toJson(ReceiptType value) {
            return value.getValue();
        }

        /**
        * Converts json ReceiptType string representation to ReceiptType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public ReceiptType fromJson(String value) {
            return EnumLookup.findEnumValue(ReceiptType.class, value, ReceiptType.Unknown.getValue());
        }
    }

	/**
	 * Converts ReprintType enums to json string representation and vice versa.
	 */
    public static class ReprintTypeAdapter {
    	
        /**
        * Converts ReprintType to json string representation.
        * 
        * @param value ReprintType value to be converted.
        */
        @ToJson
        public String toJson(ReprintType value) {
            return value.getValue();
        }

        /**
        * Converts json ReprintType string representation to ReprintType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public ReprintType fromJson(String value) {
            return EnumLookup.findEnumValue(ReprintType.class, value);
        }
    }

	/**
	 * Converts ResponseType enums to json string representation and vice versa.
	 */
    public static class ResponseTypeAdapter {
    	
        /**
        * Converts ResponseType to json string representation.
        * 
        * @param value ResponseType value to be converted.
        */
        @ToJson
        public String toJson(ResponseType value) {
            return value.getValue();
        }

        /**
        * Converts json ResponseType string representation to ResponseType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public ResponseType fromJson(String value) {
            return EnumLookup.findEnumValue(ResponseType.class, value, ResponseType.Unknown.getValue());
        }
    }

	/**
	 * Converts SettlementType enums to json string representation and vice versa.
	 */
    public static class SettlementTypeAdapter {
    	
        /**
        * Converts SettlementType to json string representation.
        * 
        * @param value SettlementType value to be converted.
        */
        @ToJson
        public String toJson(SettlementType value) {
            return value.getValue();
        }

        /**
        * Converts json SettlementType string representation to SettlementType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public SettlementType fromJson(String value) {
            return EnumLookup.findEnumValue(SettlementType.class, value);
        }
    }

	/**
	 * Converts StatusType enums to json string representation and vice versa.
	 */
    public static class StatusTypeAdapter {
    	
        /**
        * Converts StatusType to json string representation.
        * 
        * @param value StatusType value to be converted.
        */
        @ToJson
        public String toJson(StatusType value) {
            return value.getValue();
        }

        /**
        * Converts json StatusType string representation to StatusType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public StatusType fromJson(String value) {
            return EnumLookup.findEnumValue(StatusType.class, value);
        }
    }

	/**
	 * Converts TerminalCommsType enums to json string representation and vice versa.
	 */
    public static class TerminalCommsTypeAdapter {
    	
        /**
        * Converts TerminalCommsType to json string representation.
        * 
        * @param value TerminalCommsType value to be converted.
        */
        @ToJson
        public String toJson(TerminalCommsType value) {
            return value.getValue();
        }

        /**
        * Converts json TerminalCommsType string representation to TerminalCommsType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public TerminalCommsType fromJson(String value) {
            return EnumLookup.findEnumValue(TerminalCommsType.class, value, TerminalCommsType.Unknown.getValue());
        }
    }

	/**
	 * Converts TxnType enums to json string representation and vice versa.
	 */
    public static class TxnTypeAdapter {
    	
        /**
        * Converts TxnType to json string representation.
        * 
        * @param value TxnType value to be converted.
        */
        @ToJson
        public String toJson(TxnType value) {
            return value.getValue();
        }

        /**
        * Converts json TxnType string representation to TxnType enum.
        * 
        * @param value string representation value to be converted.
        */
        @FromJson
        public TxnType fromJson(String value) {
            return EnumLookup.findEnumValue(TxnType.class, value);
        }
    }
}
