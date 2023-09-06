package com.linkly.pos.sdk.common;

import java.io.IOException;
import java.lang.reflect.Type;

import com.linkly.pos.sdk.adapters.BoolToBitString;
import com.linkly.pos.sdk.adapters.DateAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.AccountTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.CardEntryTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.CommsMethodTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.CurrencyStatusAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.EftTerminalTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.GraphicCodeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.InputTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.KeyHandlingTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.LogonTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.NetworkTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.PanSourceAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.PayPassStatusAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.QueryCardTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.ReceiptAutoPrintAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.ReceiptTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.ReprintTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.ResponseTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.SettlementTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.StatusTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.TerminalCommsTypeAdapter;
import com.linkly.pos.sdk.adapters.EnumAdapters.TxnTypeAdapter;
import com.linkly.pos.sdk.adapters.UUIDAdapter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Utility class containing methods that work with the Moshi java library.
 */
public final class MoshiUtil {

    private static final Moshi MOSHI;

    // Initializes moshi with custom adapters
    static {
        MOSHI = new Moshi.Builder()
            .add(new UUIDAdapter())
            .add(new DateAdapter())
            .add(new BoolToBitString.BoolToIntAdapter())
            .add(new LogonTypeAdapter())
            .add(new ReceiptAutoPrintAdapter())
            .add(new AccountTypeAdapter())
            .add(new CardEntryTypeAdapter())
            .add(new CommsMethodTypeAdapter())
            .add(new CurrencyStatusAdapter())
            .add(new EftTerminalTypeAdapter())
            .add(new GraphicCodeAdapter())
            .add(new InputTypeAdapter())
            .add(new KeyHandlingTypeAdapter())
            .add(new LogonTypeAdapter())
            .add(new NetworkTypeAdapter())
            .add(new PanSourceAdapter())
            .add(new PayPassStatusAdapter())
            .add(new QueryCardTypeAdapter())
            .add(new ReceiptAutoPrintAdapter())
            .add(new ReceiptTypeAdapter())
            .add(new ReprintTypeAdapter())
            .add(new ResponseTypeAdapter())
            .add(new SettlementTypeAdapter())
            .add(new StatusTypeAdapter())
            .add(new TerminalCommsTypeAdapter())
            .add(new TxnTypeAdapter())
            .build();
    }

    /**
     * Retrieves the moshi adapter for the specified class.
     * 
     * @param clasz The adapter class to retrieve from moshi.
     */
    @SuppressWarnings("unchecked")
    public static <T> JsonAdapter<T> getAdapter(Class<?> clasz) {
        return (JsonAdapter<T>) MOSHI.adapter(clasz);
    }

    /**
     * Retrieves the moshi adapter for the specified type.
     * 
     * @param type The Type adapter to retrieve from moshi.
     */
    @SuppressWarnings("unchecked")
    public static <T> JsonAdapter<T> getAdapter(Type type) {
        return (JsonAdapter<T>) MOSHI.adapter(type);
    }

    /**
     * Deserializes the provided string to an object specified by the adapter class.
     * 
     * @param value The String value to be deserialized.
     * @param clasz The adapter class to retrieve from moshi.
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String value, Class<?> clasz) throws IOException {
        JsonAdapter<?> adapter = getAdapter(clasz);
        return (T) adapter.fromJson(value);
    }

    /**
     * Deserializes the provided string to an object specified by the adapter type.
     * 
     * @param value The String value to be deserialized.
     * @param type The Type adapter to retrieve from moshi.
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String value, Type type) throws IOException {
        JsonAdapter<?> adapter = getAdapter(type);
        return (T) adapter.fromJson(value);
    }
}