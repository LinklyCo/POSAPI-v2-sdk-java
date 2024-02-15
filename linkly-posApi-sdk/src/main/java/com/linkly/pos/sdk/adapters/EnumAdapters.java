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
 * Collection of Adapters that converts project enums to {@link String} representation and vice
 * versa.
 */
public class EnumAdapters {

    /**
     * Converts {@link AccountType} to {@link String} representation and vice versa.
     */
    public static class AccountTypeAdapter {

        /**
         * Converts {@link AccountType} to {@link String} representation.
         * 
         * @param value
         *            AccountType value to be converted.
         * @return equivalent String value of AccountType
         */
        @ToJson
        public String toJson(AccountType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link AccountType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return AccountType enum from String
         */
        @FromJson
        public AccountType fromJson(String value) {
            return EnumLookup.findEnumValue(AccountType.class, value);
        }
    }

    /**
     * Converts {@link CardEntryType} to {@link String} representation and vice versa.
     */
    public static class CardEntryTypeAdapter {

        /**
         * Converts {@link CardEntryType} to {@link String} representation.
         * 
         * @param value
         *            CardEntryType value to be converted.
         * @return equivalent String value of CardEntryType
         */
        @ToJson
        public String toJson(CardEntryType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link CardEntryType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return CardEntryType enum from String
         */
        @FromJson
        public CardEntryType fromJson(String value) {
            return EnumLookup.findEnumValue(CardEntryType.class, value);
        }
    }

    /**
     * Converts {@link CommsMethodType} to {@link String} representation and vice versa.
     */
    public static class CommsMethodTypeAdapter {

        /**
         * Converts {@link CommsMethodType} to {@link String} representation.
         * 
         * @param value
         *            CommsMethodType value to be converted.
         * @return equivalent String value of CommsMethodType
         */
        @ToJson
        public String toJson(CommsMethodType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link CommsMethodType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return CommsMethodType enum from String
         */
        @FromJson
        public CommsMethodType fromJson(String value) {
            return EnumLookup.findEnumValue(CommsMethodType.class, value);
        }
    }

    /**
     * Converts {@link CurrencyStatus} to {@link String} representation and vice versa.
     */
    public static class CurrencyStatusAdapter {

        /**
         * Converts {@link CurrencyStatus} to {@link String} representation.
         * 
         * @param value
         *            CurrencyStatus value to be converted.
         * @return equivalent String value of CurrencyStatus
         */
        @ToJson
        public String toJson(CurrencyStatus value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link CurrencyStatus}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return CurrencyStatus enum from String
         */
        @FromJson
        public CurrencyStatus fromJson(String value) {
            return EnumLookup.findEnumValue(CurrencyStatus.class, value);
        }
    }

    /**
     * Converts {@link EftTerminalType} to {@link String} representation and vice versa.
     */
    public static class EftTerminalTypeAdapter {

        /**
         * Converts {@link EftTerminalType} to {@link String} representation.
         * 
         * @param value
         *            EftTerminalType value to be converted.
         * @return equivalent String value of EftTerminalType
         */
        @ToJson
        public String toJson(EftTerminalType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link EftTerminalType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return EftTerminalType enum from String
         */
        @FromJson
        public EftTerminalType fromJson(String value) {
            return EnumLookup.findEnumValue(EftTerminalType.class, value);
        }
    }

    /**
     * Converts {@link GraphicCode} to String representation and vice versa.
     */
    public static class GraphicCodeAdapter {

        /**
         * Converts {@link GraphicCode} to {@link String} representation.
         * 
         * @param value
         *            GraphicCode value to be converted.
         * @return equivalent String value of GraphicCode
         */
        @ToJson
        public String toJson(GraphicCode value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link GraphicCode}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return GraphicCode enum from String
         */
        @FromJson
        public GraphicCode fromJson(String value) {
            return EnumLookup.findEnumValue(GraphicCode.class, value);
        }
    }

    /**
     * Converts {@link InputType} enums to {@link String} representation and vice versa.
     */
    public static class InputTypeAdapter {

        /**
         * Converts {@link InputType} to {@link String} representation.
         * 
         * @param value
         *            InputType value to be converted.
         * @return equivalent String value of InputType
         */
        @ToJson
        public String toJson(InputType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link InputType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return InputType enum from String
         */
        @FromJson
        public InputType fromJson(String value) {
            return EnumLookup.findEnumValue(InputType.class, value);
        }
    }

    /**
     * Converts {@link KeyHandlingType} to {@link String} representation and vice versa.
     */
    public static class KeyHandlingTypeAdapter {

        /**
         * Converts {@link KeyHandlingType} to {@link String} representation.
         * 
         * @param value
         *            KeyHandlingType value to be converted.
         * @return equivalent String value of KeyHandlingType
         */
        @ToJson
        public String toJson(KeyHandlingType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link KeyHandlingType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return KeyHandlingType enum from String
         */
        @FromJson
        public KeyHandlingType fromJson(String value) {
            return EnumLookup.findEnumValue(KeyHandlingType.class, value);
        }
    }

    /**
     * Converts {@link LogonType} to {@link String} representation and vice versa.
     */
    public static class LogonTypeAdapter {

        /**
         * Converts {@link LogonType} to {@link String} representation.
         * 
         * @param value
         *            LogonType value to be converted.
         * @return equivalent String value of LogonType
         */
        @ToJson
        public String toJson(LogonType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link LogonType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return LogonType enum from String
         */
        @FromJson
        public LogonType fromJson(String value) {
            return EnumLookup.findEnumValue(LogonType.class, value);
        }
    }

    /**
     * Converts {@link NetworkType} to {@link String} representation and vice versa.
     */
    public static class NetworkTypeAdapter {

        /**
         * Converts {@link NetworkType} to {@link String} representation.
         * 
         * @param value
         *            NetworkType value to be converted.
         * @return equivalent String value of NetworkType
         */
        @ToJson
        public String toJson(NetworkType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link NetworkType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return NetworkType enum from String
         */
        @FromJson
        public NetworkType fromJson(String value) {
            return EnumLookup.findEnumValue(NetworkType.class, value, NetworkType.Unknown
                .getValue());
        }
    }

    /**
     * Converts {@link PanSource} to {@link String} representation and vice versa.
     */
    public static class PanSourceAdapter {

        /**
         * Converts {@link PanSource} to {@link String} representation.
         * 
         * @param value
         *            PanSource value to be converted.
         * @return equivalent String value of PanSource
         */
        @ToJson
        public String toJson(PanSource value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link PanSource}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return PanSource enum from String
         */
        @FromJson
        public PanSource fromJson(String value) {
            return EnumLookup.findEnumValue(PanSource.class, value);
        }
    }

    /**
     * Converts {@link PayPassStatus} to {@link String} representation and vice versa.
     */
    public static class PayPassStatusAdapter {

        /**
         * Converts {@link PayPassStatus} to {@link String} representation.
         * 
         * @param value
         *            PayPassStatus value to be converted.
         * @return equivalent String value of PayPassStatus
         */
        @ToJson
        public String toJson(PayPassStatus value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link PayPassStatus}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return PayPassStatus enum from String
         */
        @FromJson
        public PayPassStatus fromJson(String value) {
            return EnumLookup.findEnumValue(PayPassStatus.class, value, PayPassStatus.Unknown
                .getValue());
        }
    }

    /**
     * Converts {@link QueryCardType} to {@link String} representation and vice versa.
     */
    public static class QueryCardTypeAdapter {

        /**
         * Converts {@link QueryCardType} to {@link String} representation.
         * 
         * @param value
         *            QueryCardType value to be converted.
         * @return equivalent String value of QueryCardType
         */
        @ToJson
        public String toJson(QueryCardType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link QueryCardType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return QueryCardType enum from String
         */
        @FromJson
        public QueryCardType fromJson(String value) {
            return EnumLookup.findEnumValue(QueryCardType.class, value);
        }
    }

    /**
     * Converts {@link ReceiptAutoPrint} to {@link String} representation and vice versa.
     */
    public static class ReceiptAutoPrintAdapter {

        /**
         * Converts {@link ReceiptAutoPrint} to {@link String} representation.
         * 
         * @param value
         *            ReceiptAutoPrint value to be converted.
         * @return equivalent String value of ReceiptAutoPrint
         */
        @ToJson
        public String toJson(ReceiptAutoPrint value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link ReceiptAutoPrint}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return ReceiptAutoPrint enum from String
         */
        @FromJson
        public ReceiptAutoPrint fromJson(String value) {
            return EnumLookup.findEnumValue(ReceiptAutoPrint.class, value);
        }
    }

    /**
     * Converts {@link ReceiptType} to {@link String} representation and vice versa.
     */
    public static class ReceiptTypeAdapter {

        /**
         * Converts {@link ReceiptType} to {@link String} representation.
         * 
         * @param value
         *            ReceiptType value to be converted.
         * @return equivalent String value of ReceiptType
         */
        @ToJson
        public String toJson(ReceiptType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link ReceiptType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return ReceiptType enum from String
         */
        @FromJson
        public ReceiptType fromJson(String value) {
            return EnumLookup.findEnumValue(ReceiptType.class, value, ReceiptType.Unknown
                .getValue());
        }
    }

    /**
     * Converts {@link ReprintType} to {@link String} representation and vice versa.
     */
    public static class ReprintTypeAdapter {

        /**
         * Converts {@link ReprintType} to {@link String} representation.
         * 
         * @param value
         *            ReprintType value to be converted.
         * @return equivalent String value of ReprintType
         */
        @ToJson
        public String toJson(ReprintType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link ReprintType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return ReprintType enum from String
         */
        @FromJson
        public ReprintType fromJson(String value) {
            return EnumLookup.findEnumValue(ReprintType.class, value);
        }
    }

    /**
     * Converts {@link ResponseType} to {@link String} representation and vice versa.
     */
    public static class ResponseTypeAdapter {

        /**
         * Converts {@link ResponseType} to {@link String} representation.
         * 
         * @param value
         *            ResponseType value to be converted.
         * @return equivalent String value of ResponseType
         */
        @ToJson
        public String toJson(ResponseType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link ResponseType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return ResponseType enum from String
         */
        @FromJson
        public ResponseType fromJson(String value) {
            return EnumLookup.findEnumValue(ResponseType.class, value, ResponseType.Unknown
                .getValue());
        }
    }

    /**
     * Converts {@link SettlementType} to {@link String} representation and vice versa.
     */
    public static class SettlementTypeAdapter {

        /**
         * Converts {@link SettlementType} to {@link String} representation.
         * 
         * @param value
         *            SettlementType value to be converted.
         * @return equivalent String value of SettlementType
         */
        @ToJson
        public String toJson(SettlementType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link SettlementType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return SettlementType enum from String
         */
        @FromJson
        public SettlementType fromJson(String value) {
            return EnumLookup.findEnumValue(SettlementType.class, value);
        }
    }

    /**
     * Converts {@link StatusType} to {@link String} representation and vice versa.
     */
    public static class StatusTypeAdapter {

        /**
         * Converts {@link StatusType} to {@link String} representation.
         * 
         * @param value
         *            StatusType value to be converted.
         * @return equivalent String value of StatusType
         */
        @ToJson
        public String toJson(StatusType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link StatusType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return StatusType enum from String
         */
        @FromJson
        public StatusType fromJson(String value) {
            return EnumLookup.findEnumValue(StatusType.class, value);
        }
    }

    /**
     * Converts {@link TerminalCommsType} to {@link String} representation and vice versa.
     */
    public static class TerminalCommsTypeAdapter {

        /**
         * Converts {@link TerminalCommsType} to {@link String} representation.
         * 
         * @param value
         *            TerminalCommsType value to be converted.
         * @return equivalent String value of TerminalCommsType
         */
        @ToJson
        public String toJson(TerminalCommsType value) {
            return value.getValue();
        }

        /**
         * Converts String representation to {@link TerminalCommsType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return TerminalCommsType enum from String
         */
        @FromJson
        public TerminalCommsType fromJson(String value) {
            return EnumLookup.findEnumValue(TerminalCommsType.class, value,
                TerminalCommsType.Unknown.getValue());
        }
    }

    /**
     * Converts {@link TxnType} to {@link String} representation and vice versa.
     */
    public static class TxnTypeAdapter {

        /**
         * Converts {@link TxnType} to {@link String} representation.
         * 
         * @param value
         *            TxnType value to be converted.
         * @return equivalent String value of TxnType
         */
        @ToJson
        public String toJson(TxnType value) {
            return value.getValue();
        }

        /**
         * Converts {@link String} representation to {@link TxnType}.
         * 
         * @param value
         *            String representation value to be converted.
         * @return TxnType enum from String
         */
        @FromJson
        public TxnType fromJson(String value) {
            return EnumLookup.findEnumValue(TxnType.class, value);
        }
    }
}
