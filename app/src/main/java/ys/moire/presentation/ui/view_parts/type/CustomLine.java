package ys.moire.presentation.ui.view_parts.type;

import android.graphics.Path;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * custom line class.
 */
public class CustomLine extends BaseType {

    private static final String TAG = "CustomLine";

    private static final int LINE_A = 0;
    private static final int LINE_B = 1;

    public Path path;

    public List<PointF> pathList;

//    private final Object lock = new Object();

    public CustomLine() {
        path  = new Path();
        pathList = new ArrayList<PointF>();
    }

    /**
     * scroll custom line automatically.
     * @param dx dx
     */
    @Override
    public void autoMove(final float dx) {
        for(PointF point : pathList) {
            point.x += dx;
        }
        translateToPath();
    }

    /**
     * check if the custom line is out of range.
     * @param layoutWidth screen width
     * @param slope no use with custom line
     */
    void checkOutOfRange(final int whichLine, final int layoutWidth, final int slope) {
        int underZeroCount = 0;
        int overWidthCount = 0;
        float diff = 0;
        float minX = 0;
        float maxX = 0;
        for(PointF point : pathList) {
            if(whichLine == LINE_A) {
                if(layoutWidth < point.x) {
                    overWidthCount++;
                    if(point.x < minX) {
                        minX = point.x;
                    }
                    if(maxX < point.x) {
                        maxX = point.x;
                    }
                }
            }
            else if(whichLine == LINE_B) {
                if(point.x < 0) {
                    underZeroCount++;
                    if(point.x < minX) {
                        minX = point.x;
                    }
                    if(maxX < point.x) {
                        maxX = point.x;
                    }
                }
            }
        }
        diff = maxX - minX;
        // less than 0
        if(whichLine == LINE_B && underZeroCount == pathList.size()) {
            for(PointF point : pathList) {
                point.x = layoutWidth+point.x;
            }
            translateToPath();
        }
        // more than width
        else if(whichLine == LINE_A && overWidthCount == pathList.size()) {
            for(PointF point : pathList) {
                point.x = -diff+point.x;
            }
            translateToPath();
        }
    }

    /**
     * add moving value.
     * @param valX  dx
     * @param valY  dy
     */
    public void drawOriginalLine(final float valX, final float valY, final int moveCount) {
        if(moveCount == 0) {
            startPath(valX, valY);
        }
        else {
            addLineToPath(valX, valY);
        }
    }

    private void startPath(final float x, final float y) {
        path.reset();
        path.moveTo(x, y);

        pathList.clear();
        pathList.add(new PointF(x, y));
    }

    private void addLineToPath(final float x, final float y) {
        path.lineTo(x, y);
        pathList.add(new PointF(x, y));
    }

    private void translateToPath() {
        int num = 0;
        for(PointF point : pathList) {
            if(num == 0) {
                path.reset();
                path.moveTo(point.x, point.y);
            }
            else {
                path.lineTo(point.x, point.y);
            }
            num++;
        }
    }

}
