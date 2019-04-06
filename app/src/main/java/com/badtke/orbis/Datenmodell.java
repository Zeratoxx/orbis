package com.badtke.orbis;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static android.content.Context.MODE_PRIVATE;


public class Datenmodell implements Serializable {

    //******** Instance ********
    private static Datenmodell instance = null;
    public static Datenmodell getInstance() {
        if (instance == null) {
            instance = new Datenmodell();
        }
        return instance;
    }


    //******** Attribute ********

    private String ipAdresse;
    private boolean pipProgrammSollGeandertWerden;
    private boolean favoritenSollenAngezeigtWerden;
    private boolean volumeMuteState;  //true = muted, false = not muted
    private boolean zoomState;        //true = 1 = zoom an
    private boolean pipActivityAktiv; //true = PicInPic Mode active
    private boolean standby;          //true = standby active
    private boolean pause;            //true = pause active
    private int volume;
    private int channelMainPosition;
    private int channelPipPosition;

    private ChannelList myChannelList;

    private transient MyAdapter channelAdapter;
    private transient MySortAdapter channelSortAdapter;

    private ArrayList<String> alleProgrammNamen;
    private ArrayList<String> alleChannelNamen;
    private boolean progFavState[];
    private ArrayList<Integer> favoritenIndex;
    private ArrayList<String> favoriten;


    private long zeitStart;
    private long timeShift;


    private Datenmodell()
    {
        ipAdresse = "0";
        channelMainPosition = -1;
        channelPipPosition = -1;
        favoritenSollenAngezeigtWerden = false;
        volumeMuteState = false;
        zoomState = false;
        pipActivityAktiv = false;
        standby = false;
        alleProgrammNamen = new ArrayList<String>();
        favoritenIndex = new ArrayList<Integer>();
        favoriten = new ArrayList<String>();
        alleChannelNamen = new ArrayList<String>();
        volume = 20;
        zeitStart = 0;
        timeShift = 0;
    }

    public void copyData(Datenmodell dm)
    {
        ipAdresse = dm.getIpAdresse();
        pipProgrammSollGeandertWerden = dm.PipProgrammSollGeandertWerden();
        favoritenSollenAngezeigtWerden = dm.FavoritenSollenAngezeigtWerden();
        volumeMuteState = dm.isVolumeMuteState();
        zoomState = dm.isZoomState();
        pipActivityAktiv = dm.isPipActivityAktiv();
        standby = dm.isStandby();
        pause = dm.isPause();
        volume = dm.getVolume();
        channelMainPosition = dm.getChannelMainPosition();
        channelPipPosition = dm.getChannelPipPosition();

        myChannelList = dm.getMyChannelList();
        alleProgrammNamen = dm.getAlleProgrammNamen();
        progFavState = dm.getprogFavStateFull();
        favoriten = dm.getFavoriten();
        favoritenIndex = dm.getFavoritenIndex();
        alleChannelNamen = dm.getAlleChannelNamen();
        timeShift = dm.getTimeShift();
        zeitStart = dm.getZeitStart();
    }

    public boolean isVolumeMuteState()
    {
        return volumeMuteState;
    }
    public void setVolumeMuteState(boolean b)
    {
        this.volumeMuteState = b;
    }

    public boolean isZoomState() {
        return zoomState;
    }
    public void setZoomState(boolean zoomState) {
        this.zoomState = zoomState;
    }

    public boolean isPipActivityAktiv() {
        return pipActivityAktiv;
    }
    public void setPipActivityAktiv(boolean pipActivityAktiv) {
        this.pipActivityAktiv = pipActivityAktiv;
    }

    public boolean isStandby() {
        return standby;
    }
    public void setStandby(boolean standby) {
        this.standby = standby;
    }

    public boolean isPause() {
        return pause;
    }
    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public int getVolume()
    {
        return volume;
    }
    public void setVolume(int i)
    {
        this.volume = i;
    }

    public ChannelList getMyChannelList() {
        return myChannelList;
    }
    public void setMyChannelList(ChannelList myChannelList) {
        this.myChannelList = myChannelList;
    }

    public ArrayList<String> getAlleProgrammNamen() {
        return alleProgrammNamen;
    }
    public void setAlleProgrammNamen(ArrayList<String> alleProgrammNamen) {
        this.alleProgrammNamen = alleProgrammNamen;
        progFavState = new boolean [alleProgrammNamen.size()];
        for(int i = 0; i < alleProgrammNamen.size(); i++)
        {
            progFavState[i] = false;
        }
    }


    public ArrayList<String> getAlleChannelNamen() {
        return alleChannelNamen;
    }
    public void setAlleChannelNamen(ArrayList<String> alleChannelNamen) {
        this.alleChannelNamen = alleChannelNamen;
    }


    public boolean getprogFavState(int i)
    {
        return progFavState[i];
    }
    public boolean[] getprogFavStateFull()
    {
        return progFavState;
    }
    public ArrayList<String> getFavoriten() {
        return favoriten;
    }

    public ArrayList<Integer> getFavoritenIndex() {
        return favoritenIndex;
    }

    public void addFavorit(String s, int i) {
        this.favoriten.add(s);
        this.favoritenIndex.add(i);
        this.progFavState[i] = true;
    }
    public void deletetFavorit(Integer i)
    {
        this.favoriten.remove(favoritenIndex.indexOf(i));
        this.favoritenIndex.remove(favoritenIndex.indexOf(i));
        this.progFavState[i] = false;
    }

    public MyAdapter getChannelAdapter() {
        return channelAdapter;
    }
    public void setChannelAdapter(MyAdapter channelAdapter) {
        this.channelAdapter = channelAdapter;
    }

    public MySortAdapter getChannelSortAdapter() {
        return channelSortAdapter;
    }
    public void setChannelSortAdapter(MySortAdapter channelSortAdapter) {
        this.channelSortAdapter = channelSortAdapter;
    }

    public void updateChannelAdapter(){

        if(FavoritenSollenAngezeigtWerden()) {
            channelAdapter.getData().clear();
            channelAdapter.getData().addAll(getFavoriten());
        } else {
            channelAdapter.getData().clear();
            channelAdapter.getData().addAll(getAlleProgrammNamen());
        }
        this.channelAdapter.notifyDataSetChanged();
    }


    public boolean FavoritenSollenAngezeigtWerden() {
        return favoritenSollenAngezeigtWerden;
    }
    public void setFavoritenSollenAngezeigtWerden(boolean favoritenSollenAngezeigtWerden) {
        this.favoritenSollenAngezeigtWerden = favoritenSollenAngezeigtWerden;
    }

    public boolean PipProgrammSollGeandertWerden() {
        return pipProgrammSollGeandertWerden;
    }
    public void setPipProgrammSollGeandertWerden(boolean pipProgrammSollGeandertWerden) {
        this.pipProgrammSollGeandertWerden = pipProgrammSollGeandertWerden;
    }

    public int getChannelMainPosition() {
        return channelMainPosition;
    }
    public void setChannelMainPosition(int channelMainPosition) {
        this.channelMainPosition = channelMainPosition;
    }

    public int getChannelPipPosition() {
        return channelPipPosition;
    }
    public void setChannelPipPosition(int channelPipPosition) {
        this.channelPipPosition = channelPipPosition;
    }

    public String getIpAdresse() {
        return ipAdresse;
    }
    public void setIpAdresse(String ipAdresse) {
        this.ipAdresse = ipAdresse;
    }

    public void deleteFavoriten()
    {
        this.favoriten.clear();
        this.favoritenIndex.clear();
        this.progFavState = new boolean[alleProgrammNamen.size()];
    }

    public void setZeitStart(long zeitStart) {
        this.zeitStart = zeitStart;
    }

    public void messeTimeShift(long endZeit) {
        timeShift = timeShift + ((endZeit - zeitStart)/1000);
    }

    public long getZeitStart() {
        return this.zeitStart;
    }
    public long getTimeShift(){
        return this.timeShift;
    }
    public void resetTimeShift() {
        this.timeShift = 0;
    }


    public void resetAufnahme() {
        this.setPause(false);
        this.setZeitStart(0);
        this.resetTimeShift();
    }


    protected void datenmodellDeserialisieren(Context context) throws IOException, ClassNotFoundException {
        FileInputStream in = context.openFileInput("datei.ser");
        ObjectInputStream s = new ObjectInputStream(in);
        Datenmodell eingelesenesDatenmodell = (Datenmodell) s.readObject();
        this.copyData(eingelesenesDatenmodell);
        s.close();
        in.close();
    }

    protected void datenmodellSerialisieren(Context context) throws IOException {
        FileOutputStream output;
        try{
            output = context.openFileOutput("datei.ser", MODE_PRIVATE);
        } catch (FileNotFoundException e)
        {
            File file = new File(context.getFilesDir(), "datei.ser");
            output = context.openFileOutput("datei.ser", MODE_PRIVATE);
        }
        ObjectOutputStream s = new ObjectOutputStream(output);
        s.writeObject(this);
        s.close();
        output.close();
    }

    private class ChannelList {
    }

    private class MyAdapter {
        public Collection<String> getData() {
            return null;
        }

        public void notifyDataSetChanged() {
        }
    }

    private class MySortAdapter {
    }
}
