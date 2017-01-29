package com.vz.travian.model;


import java.util.List;
import java.util.Map;


/**
 * Created by 4shae on 28.01.2017.
 */
public class MapDetailsResponse
{

    private List<MapDetails> cache;


    public List<MapDetails> getCache()
    {
        return cache;
    }


    public void setCache(List<MapDetails> cache)
    {
        this.cache = cache;
    }


    public static class MapDetails
    {
        private String name;

        private Data data;


        public String getName()
        {
            return name;
        }


        public void setName(String name)
        {
            this.name = name;
        }


        public Data getData()
        {
            return data;
        }


        public void setData(Data data)
        {
            this.data = data;
        }


        public static class Data
        {

            private Troops troops;


            public Troops getTroops()
            {
                return troops;
            }


            public void setTroops(Troops troops)
            {
                this.troops = troops;
            }


            public static class Troops
            {

                private String tribeId;

                private Map<Integer, String> units;


                public String getTribeId()
                {
                    return tribeId;
                }


                public void setTribeId(String tribeId)
                {
                    this.tribeId = tribeId;
                }


                public Map<Integer, String> getUnits()
                {
                    return units;
                }


                public void setUnits(Map<Integer, String> units)
                {
                    this.units = units;
                }
            }
        }

    }
}
