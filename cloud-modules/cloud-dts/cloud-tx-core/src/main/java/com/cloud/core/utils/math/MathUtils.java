/**
 * <p>文件名称: MathUtils.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils.math;

import java.math.BigDecimal;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class MathUtils
{

    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    public static double add(double... ds)
    {
        BigDecimal count = new BigDecimal("0");
        if (ds != null && ds.length > 0)
        {
            for (double d : ds)
            {
                BigDecimal val = new BigDecimal(Double.toString(d));
                count = count.add(val);
            }
        }
        return count.doubleValue();
    }

    public static double sub(double... ds)
    {
        if (ds == null)
            return 0;
        BigDecimal count = new BigDecimal(String.valueOf(ds[0]));
        if (ds != null && ds.length > 0)
        {
            int i = 0;
            for (double d : ds)
            {
                i++;
                if (i == 1)
                    continue;
                BigDecimal val = new BigDecimal(Double.toString(d));
                count = count.subtract(val);
            }
        }
        return count.doubleValue();
    }

    public static double mul(double... ds)
    {
        if (ds == null)
            return 0;
        BigDecimal count = new BigDecimal(String.valueOf(ds[0]));
        if (ds != null && ds.length > 0)
        {
            int i = 0;
            for (double d : ds)
            {
                i++;
                if (i == 1)
                    continue;
                BigDecimal val = new BigDecimal(Double.toString(d));
                count = count.multiply(val);
            }
        }
        return count.doubleValue();
    }

    public static double div(double... ds)
    {
        return div(DEF_DIV_SCALE, ds);
    }

    private static double div(int scale, double... ds)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        if (ds.length < 0)
        {
            throw new RuntimeException("参数无效");
        }
        BigDecimal count = new BigDecimal(String.valueOf(ds[0]));
        if (ds != null && ds.length > 0)
        {
            for (int i = 1; i < ds.length; i++)
            {
                double d = ds[i];
                BigDecimal val = new BigDecimal(Double.toString(d));
                count = count.divide(val, scale, BigDecimal.ROUND_HALF_UP);
            }
        }
        return count.doubleValue();
    }

}
