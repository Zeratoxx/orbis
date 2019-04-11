package com.badtke.orbis;

import android.content.Context;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

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


    private final int[] worldcosts = new int[] {2000,3000};
    private final int WerbungValue = 10;
    private final long TimeBetweenTwoAufgabenInMilliseconds = 10000;
    private final String fileName = "res/raw/aufgaben_sammlung.txt";
    Date date= new Date();

    private boolean firstBoot;
    private int coins;
    private String userName;
    private ArrayList<ArrayList<String>> aufgaben_sammlung;
    private int currentWorld;
    private int currentLevel;
    private int unlockedWorlds;
    private int[] settingsValues;
    private int currentAufgabe;
    private long time;
    private Timestamp lastAufgabeDone;


    private Datenmodell()
    {
        firstBoot = true;
        coins = 500;
        userName = "User";
        aufgaben_sammlung = new ArrayList<ArrayList<String>>();
        currentWorld = 0;
        currentLevel = 0;
        unlockedWorlds = 0;
        settingsValues = new int[] {};
        currentAufgabe = 0;
        time = 0;
        lastAufgabeDone = new Timestamp(0);
        aufgabenEinlesen(); //WICHTIG!!

        setNewAufgabeOrReturnFalse();

    }

    public void copyData(Datenmodell dm)
    {
        coins = dm.coins;
        firstBoot = dm.firstBoot;
        userName = dm.userName;
        aufgaben_sammlung = dm.aufgaben_sammlung;
        currentWorld = dm.currentWorld;
        currentLevel = dm.currentLevel;
        unlockedWorlds = dm.unlockedWorlds;
        settingsValues = dm.settingsValues;
        currentAufgabe = dm.currentAufgabe;
        lastAufgabeDone = dm.lastAufgabeDone;
        time = dm.time;
    }


















    public int getWorldCost(int world) {
        return worldcosts[world];
    }
    public int getWerbungValue() {
        return WerbungValue;
    }


    public int getCoins(){
        return coins;
    }
    public void setCoins(int newValue){
        coins = newValue;
    }
    public void addToCoins(int valueToAdd){
        coins += valueToAdd;
    }

    public String getUserName(){
        return userName;
    }
    public void setUserName(String name){
        userName = name;
    }

    public ArrayList<ArrayList<String>> getAufgabenSammlung(){
        return aufgaben_sammlung;
    }
    public void setAufgabenSammlung(ArrayList<ArrayList<String>> input){
        aufgaben_sammlung = input;
    }
    public ArrayList<String> getAufgabenSammlungReihe(int nummerDerAufgabe){
        return aufgaben_sammlung.get(nummerDerAufgabe);
    }
    public void setAufgabenSammlungReihe(int nummerDerAufgabe, ArrayList<String> reihe) {
            aufgaben_sammlung.set(nummerDerAufgabe, reihe);
    }
    public void addAufgabenSammlungReihe(ArrayList<String> reihe) {
        aufgaben_sammlung.add(reihe);
    }
    public String getAufgabenSchwierigkeit(int nummerDerAufgabe){
        return aufgaben_sammlung.get(nummerDerAufgabe).get(0);
    }
    public boolean aufgabeIsEasy(int nummerDerAufgabe){
        return getAufgabenSchwierigkeit(nummerDerAufgabe).equals("1");
    }
    public boolean aufgabeIsMedium(int nummerDerAufgabe){
        return getAufgabenSchwierigkeit(nummerDerAufgabe).equals("2");
    }
    public boolean aufgabeIsHeavy(int nummerDerAufgabe){
        return getAufgabenSchwierigkeit(nummerDerAufgabe).equals("3");
    }
    public String getAufgabe(int nummerDerAufgabe){
        return aufgaben_sammlung.get(nummerDerAufgabe).get(1);
    }
    public String getAufgabe(){
        return aufgaben_sammlung.get(getCurrentAufgabe()).get(1);
    }
    public String getAufgabenWert(int nummerDerAufgabe){
        return aufgaben_sammlung.get(nummerDerAufgabe).get(2);
    }
    public String getAufgabenInfo(int nummerDerAufgabe){
        return aufgaben_sammlung.get(nummerDerAufgabe).get(3);
    }
    public boolean aufgabeHatInfo(int nummerDerAufgabe){
        return aufgaben_sammlung.get(nummerDerAufgabe).get(3).equals("*");
    }
    public boolean aufgabeAlreadyFinished(int nummerDerAufgabe) {
        return aufgaben_sammlung.get(nummerDerAufgabe).get(4).equals("finished");
    }
    public void setAufgabeFinished(int numberOfAufgabe){
        aufgaben_sammlung.get(numberOfAufgabe).set(4,"finished");
    }
    public void setCurrentAufgabeFinished(){
        setAufgabeFinished(currentAufgabe);
    }

    public int getCurrentWorld(){
        return currentWorld;
    }
    public void setCurrentWorld(int currentWorld){
        this.currentWorld = currentWorld;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
    public void setCurrentLevel(int numberOfLevel) {
        currentLevel = numberOfLevel;
    }
    public void increaseCurrentLevelByOne() {
        increaseCurrentLevel(1);
    }
    public void increaseCurrentLevel(int amount) {
        currentLevel += amount ;
    }

    public int getUnlockedWorlds(){
        return unlockedWorlds;
    }
    public void setUnlockedWorlds(int amount){
        unlockedWorlds = amount;
    }
    //public void unlockNextWorld(){ unlockedWorlds += 1; }

    public int[] getSettingsValues() {
        return settingsValues;
    }
    public int getSettingValue(int postitionOfSetting) {
        return settingsValues[postitionOfSetting];
    }
    public void setSettingValue(int postitionOfSetting, int valueOfSetting) {
        settingsValues[postitionOfSetting] = valueOfSetting;
    }
    /*public int getSettingValue(String nameOfSetting) {
        int postitionOfSetting = 0;
        if (nameOfSetting == "mute") postitionOfSetting = 0;
        return settingsValues[postitionOfSetting];
    }*/

    public int getCurrentAufgabe(){
        return currentAufgabe;
    }
    public void setCurrentAufgabe(int newAufgabe){
        currentAufgabe = newAufgabe;
    }
    private int randomizeNextCurrentAufgabe() {
        int difficulty = getCurrentWorld() + 1;

        Random randomizer = new Random();
        int newAufgabe;

        do {
            newAufgabe = randomizer.nextInt(aufgaben_sammlung.size());
        } while (aufgabeAlreadyFinished(newAufgabe) && Integer.parseInt(getAufgabenSchwierigkeit(newAufgabe)) != difficulty);

        return newAufgabe;
    }
    public int getAmountOfAufgaben() {
        return aufgaben_sammlung.size();
    }

    public boolean isFirstBoot() {
        return firstBoot;
    }
    public void noMoreFirstBoot() {
        firstBoot = false;
    }
    public void resetFirstBoot(){
        firstBoot = true;
    }

    private void setLastAufgabeDone(long ms){
        lastAufgabeDone = new Timestamp(ms);
    }
    private void setLastAufgabeDone(Timestamp ts){
        lastAufgabeDone = ts;
    }
    public Timestamp getTimestampOfLastAufgabeDone(){
        if(lastAufgabeDone!=null)
            return lastAufgabeDone;
        else return new Timestamp(0);

    }

    public boolean setNewAufgabeOrReturnFalse(){
        boolean i = false;
        if( newAufgabeIsAvailable() ){
            currentAufgabe = randomizeNextCurrentAufgabe();
            currentLevel++;
            i = true;
        }
        return i;
    }
    public ArrayList<String> getNewAufgabe(){
        setNewAufgabeOrReturnFalse();

        return getAufgabenSammlungReihe(currentAufgabe);
    }
    public void aufgabeGeschafft(){
        setCurrentAufgabeFinished();
        addToCoins(Integer.valueOf(getAufgabenWert(getCurrentAufgabe())));
        lastAufgabeDone = new Timestamp(date.getTime());

    }

    public boolean newAufgabeIsAvailable() {
        return (date.getTime() - lastAufgabeDone.getTime()) >= TimeBetweenTwoAufgabenInMilliseconds || lastAufgabeDone.equals(new Timestamp(0));
    }
























    /** @brief Initial read of all tasks to do.
     * Reads the external file of all tasks and saves it to an array.
     * Adds a field to every Line with the value "unfinished".
     *
     * @return void
     */
    private void aufgabenEinlesen() {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader()).getResourceAsStream(fileName)), ';');
            String [] nextLine;
            int line = 0;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line

                aufgaben_sammlung.add(new ArrayList<String>());

                for (String aNextLine : nextLine) {

                    aufgaben_sammlung.get(line).add(aNextLine); //one Line of aufgabenSammlung:
                }
                //Log.d("VariableTag", nextLine[0]);
                aufgaben_sammlung.get(line).add("unfinished");

                line++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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















/*

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
*/
/*
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
*/
/*
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
*/



   /* private class ChannelList {
    }*/

   /*
    private class MyAdapter {
        public Collection<String> getData() {
            return null;
        }

        public void notifyDataSetChanged() {
        }
    }

    private class MySortAdapter {
    }
    */

}
