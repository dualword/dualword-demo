package org.dualword.testgps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Iterator;

public class SatView extends View {
    private GpsStatus status;
    private Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int w, h, r, count;

    public SatView(Context context) {
        super(context);
    }

    public SatView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setStatus(GpsStatus status) {
        this.status = status;
        count = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
        drawSat(canvas);
        drawText(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        this.h = h;
        r = (Math.min(w, h) / 2) - 10;
    }

    private void drawGrid(Canvas canvas) {
        p.setStyle(Paint.Style.FILL);
        p.setColor(Color.parseColor("#D3D3D3"));
        canvas.drawCircle(w / 2, h / 2, r, p);
    }

    private void drawSat(Canvas canvas) {
        p.setColor(Color.parseColor("#000000"));
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(3);
        if(status != null) {
            Iterator<GpsSatellite> gpssat = status.getSatellites().iterator();
            while (gpssat.hasNext()) {
                GpsSatellite sat = gpssat.next();
                if(sat.getSnr() <= 0) continue;
                count++;
                //Log.d("GpsSatellite", sat.getSnr()+":"+sat.getPrn() + ":" + sat.getAzimuth() + ":" + sat.getElevation() + "");
                canvas.drawCircle((float)(w / 2 + sat.getAzimuth()), (float)(w / 2 - sat.getElevation()),
                        (sat.getSnr()+10), p);
            }
        }
    }

    private void drawText(Canvas canvas) {
        p.setColor(Color.parseColor("#D3D3D3"));
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setStrokeWidth(3);
        p.setTextSize(50);
        canvas.drawText("FirstFix:" + (status == null ? 0 : status.getTimeToFirstFix()), 10, h - 110, p);
        canvas.drawText("Satellites:" + count, 10, h - 55, p);
        canvas.drawText("MaxSatellites:" + (status == null ? 0 : status.getMaxSatellites()), 10, h - 10, p);
    }

}
