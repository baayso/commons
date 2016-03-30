package com.baayso.commons.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 计算距离工具类。
 *
 * @author ChenFangjie (2014/12/17 13:20:34)
 * @since 1.0.0
 */
public final class Distance {

    private static final double EARTH_RADIUS = 6371; // 地球半径，平均半径为6371km // 6378.137

    /**
     * 计算某个经纬度的周围某段距离的正方形的四个点。
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param distance  该点所在圆的半径，该圆与此正方形内切
     *
     * @return 正方形的四个点的经纬度坐标
     *
     * @since 1.0.0
     */
    public static Range calculateSquarePoint(double longitude, double latitude, double distance) {

        double dlng = 2 * Math.asin(Math.sin(distance / (2 * EARTH_RADIUS)) / Math.cos(Distance.deg2rad(latitude)));
        dlng = Distance.rad2deg(dlng);

        double dlat = distance / EARTH_RADIUS;
        dlat = Distance.rad2deg(dlat);

        double left_top_lat = latitude + dlat;
        double left_top_lng = longitude - dlng;

        double right_top_lat = latitude + dlat;
        double right_top_lng = longitude + dlng;

        double left_bottom_lat = latitude - dlat;
        double left_bottom_lng = longitude - dlng;

        double right_bottom_lat = latitude - dlat;
        double right_bottom_lng = longitude + dlng;

        Range range = new Range();
        range.setLeftTopLatitude(left_top_lat);
        range.setLeftTopLongitude(left_top_lng);

        range.setRightTopLatitude(right_top_lat);
        range.setRightTopLongitude(right_top_lng);

        range.setLeftBottomLatitude(left_bottom_lat);
        range.setLeftBottomLongitude(left_bottom_lng);

        range.setRightBottomLatitude(right_bottom_lat);
        range.setRightBottomLongitude(right_bottom_lng);

        return range;
    }

    /**
     * 将角度转换为弧度。
     *
     * @param degrees 角度
     *
     * @return 弧度
     *
     * @since 1.0.0
     */
    public static double deg2rad(double degrees) {
        // return (Math.PI / 180) * degrees;
        return degrees / 180 * Math.PI;
    }

    /**
     * 将弧度转换为角度。
     *
     * @param radian 弧度
     *
     * @return 角度
     *
     * @since 1.0.0
     */
    public static double rad2deg(double radian) {
        return radian * 180 / Math.PI;
    }

    /**
     * 表示一个正方形的范围。
     *
     * @author ChenFangjie (2014/12/17 13:41:11)
     * @since 1.0.0
     */
    public static class Range {

        private double leftTopLatitude; // 正方形左上角纬度
        private double leftTopLongitude; // 正方形左上角经度

        private double rightTopLatitude; // 正方形右上角纬度
        private double rightTopLongitude; // 正方形右上角经度

        private double leftBottomLatitude; // 正方形左下角纬度
        private double leftBottomLongitude; // 正方形左下角经度

        private double rightBottomLatitude; // 正方形右下角纬度
        private double rightBottomLongitude; // 正方形右下角经度

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }

        public double getLeftTopLatitude() {
            return leftTopLatitude;
        }

        public void setLeftTopLatitude(double leftTopLatitude) {
            this.leftTopLatitude = leftTopLatitude;
        }

        public double getLeftTopLongitude() {
            return leftTopLongitude;
        }

        public void setLeftTopLongitude(double leftTopLongitude) {
            this.leftTopLongitude = leftTopLongitude;
        }

        public double getRightTopLatitude() {
            return rightTopLatitude;
        }

        public void setRightTopLatitude(double rightTopLatitude) {
            this.rightTopLatitude = rightTopLatitude;
        }

        public double getRightTopLongitude() {
            return rightTopLongitude;
        }

        public void setRightTopLongitude(double rightTopLongitude) {
            this.rightTopLongitude = rightTopLongitude;
        }

        public double getLeftBottomLatitude() {
            return leftBottomLatitude;
        }

        public void setLeftBottomLatitude(double leftBottomLatitude) {
            this.leftBottomLatitude = leftBottomLatitude;
        }

        public double getLeftBottomLongitude() {
            return leftBottomLongitude;
        }

        public void setLeftBottomLongitude(double leftBottomLongitude) {
            this.leftBottomLongitude = leftBottomLongitude;
        }

        public double getRightBottomLatitude() {
            return rightBottomLatitude;
        }

        public void setRightBottomLatitude(double rightBottomLatitude) {
            this.rightBottomLatitude = rightBottomLatitude;
        }

        public double getRightBottomLongitude() {
            return rightBottomLongitude;
        }

        public void setRightBottomLongitude(double rightBottomLongitude) {
            this.rightBottomLongitude = rightBottomLongitude;
        }

    }

}
