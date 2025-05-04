package com.example.csvtosql.model;

import java.sql.Time;

public class User {
    public String  source;
    public String  dateUplode;
    public Time  timeUplode;
    public String  summary;
    public String  date;
    public String  estimatedTime;
    public String  killed;
    public String  injury;
    public String  numberofinsident;
    public String  highImpact;
    public String  sourceinforating;
    public String  locationAccuracy;
    public String  longitude;
    public String  province;
    public String  location;
    public String  activityType;
    public String  incidentType;
    public String  incidentSubtype;
    public String  instigator;
    public String  subtype;
    public String  target;
    public String  subtype1;
    public String  criminalCharges;
    public String  physicalContext;
    public String  reasonforprotest;
    public String  numberofprotesters;
    public String  pmuFaction;
    public String  tribalGroup;
    public String  majorStorm;
    public String  tactic;
    public String  uspt;
    public String  threatLevel;
    public String  diseaseOutbreak;
    public String  drought;


    public User() {

    }

    public User(String source, String dateUplode, Time timeUplode, String summary, String date, String estimatedTime, String killed, String injury, String numberofinsident, String highImpact, String sourceinforating, String locationAccuracy, String longitude, String province, String location, String activityType, String incidentType, String incidentSubtype, String instigator, String subtype, String target, String subtype1, String criminalCharges, String physicalContext, String reasonforprotest, String numberofprotesters, String pmuFaction, String tribalGroup, String majorStorm, String tactic, String uspt, String threatLevel, String diseaseOutbreak, String drought) {
        this.source = source;
        this.dateUplode = dateUplode;
        this.timeUplode = timeUplode;
        this.summary = summary;
        this.date = date;
        this.estimatedTime = estimatedTime;
        this.killed = killed;
        this.injury = injury;
        this.numberofinsident = numberofinsident;
        this.highImpact = highImpact;
        this.sourceinforating = sourceinforating;
        this.locationAccuracy = locationAccuracy;
        this.longitude = longitude;
        this.province = province;
        this.location = location;
        this.activityType = activityType;
        this.incidentType = incidentType;
        this.incidentSubtype = incidentSubtype;
        this.instigator = instigator;
        this.subtype = subtype;
        this.target = target;
        this.subtype1 = subtype1;
        this.criminalCharges = criminalCharges;
        this.physicalContext = physicalContext;
        this.reasonforprotest = reasonforprotest;
        this.numberofprotesters = numberofprotesters;
        this.pmuFaction = pmuFaction;
        this.tribalGroup = tribalGroup;
        this.majorStorm = majorStorm;
        this.tactic = tactic;
        this.uspt = uspt;
        this.threatLevel = threatLevel;
        this.diseaseOutbreak = diseaseOutbreak;
        this.drought = drought;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDateUplode() {
        return dateUplode;
    }

    public void setDateUplode(String dateUplode) {
        this.dateUplode = dateUplode;
    }

    public Time getTimeUplode() {
        return timeUplode;
    }

    public void setTimeUplode(Time timeUplode) {
        this.timeUplode = timeUplode;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getKilled() {
        return killed;
    }

    public void setKilled(String killed) {
        this.killed = killed;
    }

    public String getInjury() {
        return injury;
    }

    public void setInjury(String injury) {
        this.injury = injury;
    }

    public String getNumberofinsident() {
        return numberofinsident;
    }

    public void setNumberofinsident(String numberofinsident) {
        this.numberofinsident = numberofinsident;
    }

    public String getHighImpact() {
        return highImpact;
    }

    public void setHighImpact(String highImpact) {
        this.highImpact = highImpact;
    }

    public String getSourceinforating() {
        return sourceinforating;
    }

    public void setSourceinforating(String sourceinforating) {
        this.sourceinforating = sourceinforating;
    }

    public String getLocationAccuracy() {
        return locationAccuracy;
    }

    public void setLocationAccuracy(String locationAccuracy) {
        this.locationAccuracy = locationAccuracy;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getIncidentSubtype() {
        return incidentSubtype;
    }

    public void setIncidentSubtype(String incidentSubtype) {
        this.incidentSubtype = incidentSubtype;
    }

    public String getInstigator() {
        return instigator;
    }

    public void setInstigator(String instigator) {
        this.instigator = instigator;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSubtype1() {
        return subtype1;
    }

    public void setSubtype1(String subtype1) {
        this.subtype1 = subtype1;
    }

    public String getCriminalCharges() {
        return criminalCharges;
    }

    public void setCriminalCharges(String criminalCharges) {
        this.criminalCharges = criminalCharges;
    }

    public String getPhysicalContext() {
        return physicalContext;
    }

    public void setPhysicalContext(String physicalContext) {
        this.physicalContext = physicalContext;
    }

    public String getReasonforprotest() {
        return reasonforprotest;
    }

    public void setReasonforprotest(String reasonforprotest) {
        this.reasonforprotest = reasonforprotest;
    }

    public String getNumberofprotesters() {
        return numberofprotesters;
    }

    public void setNumberofprotesters(String numberofprotesters) {
        this.numberofprotesters = numberofprotesters;
    }

    public String getPmuFaction() {
        return pmuFaction;
    }

    public void setPmuFaction(String pmuFaction) {
        this.pmuFaction = pmuFaction;
    }

    public String getTribalGroup() {
        return tribalGroup;
    }

    public void setTribalGroup(String tribalGroup) {
        this.tribalGroup = tribalGroup;
    }

    public String getMajorStorm() {
        return majorStorm;
    }

    public void setMajorStorm(String majorStorm) {
        this.majorStorm = majorStorm;
    }

    public String getTactic() {
        return tactic;
    }

    public void setTactic(String tactic) {
        this.tactic = tactic;
    }

    public String getUspt() {
        return uspt;
    }

    public void setUspt(String uspt) {
        this.uspt = uspt;
    }

    public String getThreatLevel() {
        return threatLevel;
    }

    public void setThreatLevel(String threatLevel) {
        this.threatLevel = threatLevel;
    }

    public String getDiseaseOutbreak() {
        return diseaseOutbreak;
    }

    public void setDiseaseOutbreak(String diseaseOutbreak) {
        this.diseaseOutbreak = diseaseOutbreak;
    }

    public String getDrought() {
        return drought;
    }

    public void setDrought(String drought) {
        this.drought = drought;
    }
}
