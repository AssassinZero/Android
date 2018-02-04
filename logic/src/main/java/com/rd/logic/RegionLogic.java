package com.rd.logic;

import android.text.TextUtils;

import com.rd.logic.greendao.entity.RegionBean;
import com.rd.logic.greendao.helper.RegionDaoHelper;

import java.util.ArrayList;

/**
 * Author: TinhoXu
 * E-mail: xth@erongdu.com
 * Date: 2017/4/7 11:19
 * <p/>
 * Description:
 */
public class RegionLogic {
    /** 所在城市 - 省数据源 */
    private ArrayList<RegionBean>                       provinceList;
    /** 所在城市 - 市数据源 */
    private ArrayList<ArrayList<RegionBean>>            cityList;
    /** 所在城市 - 区数据源 */
    private ArrayList<ArrayList<ArrayList<RegionBean>>> areaList;

    private RegionLogic() {
        provinceList = new ArrayList<>();
        cityList = new ArrayList<>();
        areaList = new ArrayList<>();
    }

    public static RegionLogic getInstance() {
        return RegionLogicInstance.instance;
    }

    private static class RegionLogicInstance {
        static RegionLogic instance = new RegionLogic();
    }

    public ArrayList<RegionBean> getProvinceList() {
        if (provinceList == null || provinceList.isEmpty()) {
            provinceList = RegionDaoHelper.getInstance().getProvinces();
        }
        return provinceList;
    }

    public ArrayList<ArrayList<RegionBean>> getCityList() {
        if (cityList == null || cityList.isEmpty()) {
            ArrayList<RegionBean> cities = RegionDaoHelper.getInstance().getCities();
            for (RegionBean province : provinceList) {
                ArrayList<RegionBean> temp = new ArrayList<>();
                for (RegionBean city : cities) {
                    if (city.getPid() <= province.getNid()) {
                        if (city.getPid() == province.getNid()) {
                            temp.add(city);
                        }
                    } else {
                        break;
                    }
                }
                cityList.add(temp);
            }
        }
        return cityList;
    }

    public ArrayList<ArrayList<ArrayList<RegionBean>>> getAreaList() {
        if (areaList == null || areaList.isEmpty()) {
            ArrayList<RegionBean> areas = RegionDaoHelper.getInstance().getAreas();
            for (ArrayList<RegionBean> cities : cityList) {
                ArrayList<ArrayList<RegionBean>> temp1 = new ArrayList<>();
                for (RegionBean city : cities) {
                    ArrayList<RegionBean> temp2 = new ArrayList<>();
                    for (RegionBean area : areas) {
                        if (area.getPid() <= city.getNid()) {
                            if (area.getPid() == city.getNid()) {
                                temp2.add(area);
                            }
                        } else {
                            break;
                        }
                    }
                    temp1.add(temp2);
                }
                areaList.add(temp1);
            }
        }
        return areaList;
    }

    public void getData(int options1, int options2, int options3, OptionsSelectCallback callback) {
        RegionBean province     = null;
        RegionBean city         = null;
        RegionBean area         = null;
        Long       provinceCode = null;
        Long       cityCode     = null;
        Long       areaCode     = null;
        String     region       = "";

        // 省
        if (null != provinceList && !provinceList.isEmpty()) {
            province = provinceList.get(options1);
        }
        // 市
        if (null != cityList && !cityList.isEmpty()) {
            city = cityList.get(options1).get(options2);
        }
        // 区
        if (null != areaList && !areaList.isEmpty()) {
            area = areaList.get(options1).get(options2).get(options3);
        }

        if (null != province) {
            region += province.getName();
            provinceCode = province.getNid();
        }
        if (null != city) {
            region += "," + city.getName();
            cityCode = city.getNid();
        }
        if (null != area && !TextUtils.isEmpty(area.getName())) {
            region += "," + area.getName();
            areaCode = area.getNid();
        }
        if (null != callback) {
            callback.select(provinceCode, cityCode, areaCode, region);
        }
    }

    public interface OptionsSelectCallback {
        void select(Long provinceCode, Long cityCode, Long areaCode, String region);
    }
}