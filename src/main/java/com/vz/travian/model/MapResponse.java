package com.vz.travian.model;


import java.util.List;


public class MapResponse
{

    private Response response;


    public Response getResponse()
    {
        return response;
    }


    public void setResponse(Response response)
    {
        this.response = response;
    }


    public static class Response
    {

        private Map map;


        public Map getMap()
        {
            return map;
        }


        public void setMap(Map map)
        {
            this.map = map;
        }


        public static class Map
        {

            List<Cell> cells;


            public List<Cell> getCells()
            {
                return cells;
            }


            public void setCells(List<Cell> cells)
            {
                this.cells = cells;
            }


            public static class Cell
            {

                private String id;

                private String x;

                private String y;

                private String oasis;

                private Object kingdomId;

                private java.util.Map<Integer, String> troops;


                public String getId()
                {
                    return id;
                }


                public void setId(String id)
                {
                    this.id = id;
                }


                public String getX()
                {
                    return x;
                }


                public void setX(String x)
                {
                    this.x = x;
                }


                public String getY()
                {
                    return y;
                }


                public void setY(String y)
                {
                    this.y = y;
                }


                public String getOasis()
                {
                    return oasis;
                }


                public void setOasis(String oasis)
                {
                    this.oasis = oasis;
                }


                public java.util.Map<Integer, String> getTroops()
                {
                    return troops;
                }


                public void setTroops(java.util.Map<Integer, String> troops)
                {
                    this.troops = troops;
                }


                public Object getKingdomId()
                {
                    return kingdomId;
                }


                public void setKingdomId(Object kingdomId)
                {
                    this.kingdomId = kingdomId;
                }


                @Override
                public String toString()
                {
                    return "Cell{" + "id='" + id + '\'' + ", x='" + x + '\'' + ", y='" + y + '\'' + ", oasis='" + oasis
                            + '\'' + ", troops=" + troops + '}';
                }
            }

        }

    }

}
