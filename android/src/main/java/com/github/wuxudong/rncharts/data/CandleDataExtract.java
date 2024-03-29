package com.github.wuxudong.rncharts.data;

import android.graphics.Paint;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.wuxudong.rncharts.utils.BridgeUtils;
import com.github.wuxudong.rncharts.utils.ChartDataSetConfigUtils;
import com.github.wuxudong.rncharts.utils.ConversionUtil;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by xudong on 02/03/2017.
 */
public class CandleDataExtract extends DataExtract<CandleData, CandleEntry> {
    @Override
    CandleData createData() {
        return new CandleData();
    }

    @Override
    IDataSet<CandleEntry> createDataSet(ArrayList<CandleEntry> entries, String label) {
        return new CandleDataSet(entries, label);
    }

    @Override
    void dataSetConfig(Chart chart, IDataSet<CandleEntry> dataSet, ReadableMap config) {
        CandleDataSet candleDataSet = (CandleDataSet) dataSet;

        ChartDataSetConfigUtils.commonConfig(chart, candleDataSet, config);
        ChartDataSetConfigUtils.commonBarLineScatterCandleBubbleConfig(candleDataSet, config);
        ChartDataSetConfigUtils.commonLineScatterCandleRadarConfig(candleDataSet, config);

        // set param showCandleBar
        if (BridgeUtils.validate(config, ReadableType.Boolean, "showCandleBar")) {
            candleDataSet.setShowCandleBar(config.getBoolean("showCandleBar"));
        }
        // CandleDataSet only config
        if (BridgeUtils.validate(config, ReadableType.Number, "barSpace")) {
            candleDataSet.setBarSpace((float) config.getDouble("barSpace"));
        }
        if (BridgeUtils.validate(config, ReadableType.Number, "shadowWidth")) {
            candleDataSet.setShadowWidth((float) config.getDouble("shadowWidth"));
        }
        if (BridgeUtils.validate(config, ReadableType.Number, "shadowColor")) {
            candleDataSet.setShadowColor(config.getInt("shadowColor"));
        }
        if (BridgeUtils.validate(config, ReadableType.Boolean, "shadowColorSameAsCandle")) {
            candleDataSet.setShadowColorSameAsCandle(config.getBoolean("shadowColorSameAsCandle"));
        }
        if (BridgeUtils.validate(config, ReadableType.Number, "neutralColor")) {
            candleDataSet.setNeutralColor(config.getInt("neutralColor"));
        }
        if (BridgeUtils.validate(config, ReadableType.Number, "decreasingColor")) {
            candleDataSet.setDecreasingColor(config.getInt("decreasingColor"));
        }
        if (BridgeUtils.validate(config, ReadableType.String, "decreasingPaintStyle")) {
            candleDataSet.setDecreasingPaintStyle(Paint.Style.valueOf(config.getString("decreasingPaintStyle").toUpperCase(Locale.ENGLISH)));
        }
        if (BridgeUtils.validate(config, ReadableType.Number, "increasingColor")) {
            candleDataSet.setIncreasingColor(config.getInt("increasingColor"));
        }
        if (BridgeUtils.validate(config, ReadableType.String, "increasingPaintStyle")) {
            candleDataSet.setIncreasingPaintStyle(Paint.Style.valueOf(config.getString("increasingPaintStyle").toUpperCase(Locale.ENGLISH)));
        }
    }

    @Override
    CandleEntry createEntry(ReadableArray values, int index) {
        if (!ReadableType.Map.equals(values.getType(index))) {
            throw new IllegalArgumentException();
        }

        ReadableMap map = values.getMap(index);

        float x = index;
        if (map.hasKey("x")) {
            x = (float) map.getDouble("x");
        }

        if (!BridgeUtils.validate(map, ReadableType.Number, "H")
                || !BridgeUtils.validate(map, ReadableType.Number, "L")
                || !BridgeUtils.validate(map, ReadableType.Number, "O")
                || !BridgeUtils.validate(map, ReadableType.Number, "C")) {
            throw new IllegalArgumentException(
                    "CandleStick data must contain: H, L, O and C values");
        }

        float H = (float) map.getDouble("H");
        float L = (float) map.getDouble("L");
        float O = (float) map.getDouble("O");
        float C = (float) map.getDouble("C");

        CandleEntry candleEntry = new CandleEntry(x, H, L, O, C, ConversionUtil.toMap(map));

        return candleEntry;
    }
}
