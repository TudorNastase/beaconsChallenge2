package com.example.beaconschallenge2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    protected static final String TAG = "RangingActivity";
    private BeaconManager beaconManager;
    String file=new String("f1:a6:a4:76:14:a5,6.855355418,52.23945261,5," +
            "e6:b2:cb:4e:ac:06,6.855699127,52.23913545,4," +
            "dd:82:3b:bf:80:8e,6.855268704,52.23945603,4," +
            "fa:88:41:62:4c:2d,6.856093081,52.23963231,1," +
            "d0:20:5d:ca:96:14,6.856169459,52.23960573,1," +
            "c5:8e:8e:75:24:52,6.855337968,52.23937066,1," +
            "c1:7a:a6:f8:5e:43,6.855426516,52.23910998,1," +
            "e2:83:81:47:2c:be,6.855527136,52.23905922,2," +
            "c4:b6:ea:be:c9:95,6.855549554,52.23908741,4," +
            "c0:57:ea:07:e5:60,6.855623341,52.23905732,4," +
            "e7:b5:2e:21:55:65,6.856065505,52.23960463,4," +
            "ee:70:a9:48:23:9a,6.856144471,52.23957796,4," +
            "cd:f4:53:01:85:6f,6.85613895,52.23957809,5," +
            "f5:0a:aa:8f:49:0f,6.855547952,52.23908729,5," +
            "e2:23:a4:28:1b:d8,6.855853761,52.23939414,3," +
            "fd:ec:87:be:0d:3b,6.856135569,52.23957702,2," +
            "ce:97:cc:07:99:2b,6.855253372,52.23945255,2," +
            "c2:18:61:d0:1c:ce,6.855243821,52.23937116,2," +
            "e0:d8:5c:4e:3b:bc,6.855556279,52.23954519,2," +
            "fb:eb:d2:ac:be:80,6.8560067,52.23954666,3," +
            "d5:96:4e:f3:72:28,6.855625474,52.23916266,3," +
            "ee:4c:e4:1a:63:00,6.855565092,52.23919959,1," +
            "c5:aa:45:71:a9:aa,6.855420135,52.23938983,1," +
            "fe:17:c6:d9:3c:a4,6.855412214,52.2392901,1," +
            "cc:06:10:a9:34:42,6.855429131,52.23948178,1," +
            "d6:f1:57:0d:96:c3,6.855700465,52.23946432,1," +
            "ed:cd:09:90:aa:c1,6.85564717,52.23928667,1," +
            "ff:5c:d2:2e:d0:45,6.855670537,52.23950061,2," +
            "f1:82:f2:25:83:38,6.855776605,52.23921505,5," +
            "c0:67:de:9e:ec:27,6.855913061,52.23945059,5," +
            "d7:0f:fe:e3:84:a2,6.855498954,52.23943865,1," +
            "d6:8c:ad:3a:c5:5b,6.855336887,52.23936191,5," +
            "ea:9f:04:5f:08:26,6.855723372,52.23935636,1," +
            "dc:a9:e2:71:c8:30,6.855570612,52.23942213,1," +
            "eb:fa:f3:ce:5b:71,6.855954886,52.23948983,2," +
            "cd:5e:c3:70:3e:77,6.85555343,52.23956292,2," +
            "c7:56:8d:ed:a8:04,6.855777869,52.23931617,2," +
            "eb:32:18:c0:2e:0d,6.855233753,52.23927901,2," +
            "d2:e5:f8:aa:b9:a8,6.855323233,52.23929638,2," +
            "ee:79:08:1d:ed:a3,6.855711246,52.23924787,2," +
            "d6:50:2c:57:03:42,6.855310645,52.23918478,2," +
            "c8:61:5d:4a:39:7d,6.856023755,52.2394607,2," +
            "eb:44:e8:64:6e:33,6.856079827,52.23951846,2," +
            "f0:8d:08:33:2a:05,6.856134906,52.23957665,3," +
            "d6:80:ce:30:96:11,6.855805107,52.23936314,3," +
            "d7:60:87:1d:ae:c9,6.855712755,52.23939158,3," +
            "d5:1d:3d:ba:06:e8,6.855356705,52.23954125,2," +
            "fa:fd:f5:3a:e8:61,6.855240657,52.23935569,3," +
            "ef:22:4f:a4:18:ae,6.855336393,52.23935023,3," +
            "c8:c2:37:5f:8c:92,6.85531703,52.2392628,3," +
            "cb:36:ac:01:90:85,6.855502145,52.23947311,3," +
            "fd:e8:1f:85:c6:87,6.856066067,52.23960513,3," +
            "ed:68:73:1f:38:43,6.855835052,52.23926996,3," +
            "db:d4:c3:a8:e5:e3,6.855393914,52.23920173,3," +
            "c6:7e:4d:6e:c7:ab,6.855623359,52.23905755,3," +
            "e9:92:0f:f8:7b:ce,6.855779324,52.23931735,3," +
            "fe:ac:81:68:5b:53,6.855234353,52.23927233,3," +
            "ec:47:5e:21:ec:a0,6.855961666,52.23941742,3," +
            "fb:8a:94:14:ee:6b,6.855918188,52.23943405,3," +
            "dc:a2:cc:4c:ae:57,6.856082074,52.23951644,3," +
            "f3:24:4c:e1:37:f3,6.855932427,52.23946837,3," +
            "cb:e3:e0:fa:43:c4,6.855322527,52.23927556,4," +
            "e4:3f:42:d1:b5:bc,6.855243096,52.23936697,4," +
            "ed:52:32:65:ba:77,6.855679448,52.23922184,4," +
            "df:ad:ec:25:d3:df,6.855281762,52.23961528,3," +
            "db:aa:a5:a4:27:51,6.855448861,52.23960095,1," +
            "c6:fa:48:ea:dd:09,6.855393879,52.23920106,1," +
            "f2:07:e2:5d:b1:4c,6.855625774,52.23939269,1," +
            "e7:90:24:c6:38:d6,6.855358579,52.23958731,4," +
            "ed:51:58:b5:6a:10,6.855930827,52.23936574,1," +
            "c5:56:85:a3:5d:20,6.855336245,52.23931973,1," +
            "f1:af:52:4f:fb:0f,6.8553662,52.23950501,3," +
            "c9:c7:8d:08:f9:1a,6.855573563,52.23920411,3," +
            "f3:d3:ff:6d:5f:52,6.855280981,52.23961612,4," +
            "dd:97:50:b7:f0:f9,6.855550487,52.23908727,3," +
            "d0:9a:dc:ba:40:85,6.855844101,52.23929091,4," +
            "fb:cc:c0:e8:47:07,6.855835666,52.23937283,4," +
            "fd:be:64:11:a7:ab,6.856064977,52.23960481,2," +
            "d6:31:92:f8:be:a2,6.85578645,52.23922151,2," +
            "ef:03:ce:2e:2c:86,6.855698662,52.23913347,2," +
            "f1:db:77:f8:d2:47,6.85523582,52.23918911,2," +
            "c3:ec:17:db:1e:a1,6.855378079,52.23959829,2," +
            "cb:66:0b:bb:75:52,6.855367163,52.23963249,4," +
            "e8:3b:d0:e6:d3:40,6.85535487,52.23944842,3," +
            "fb:30:be:cd:88:4c,6.855988243,52.23952519,5," +
            "c9:37:27:60:04:a9,6.855855898,52.23928832,1," +
            "c8:36:86:3b:97:d9,6.855622334,52.2391612,2," +
            "f2:e7:1b:cb:5e:17,6.855853528,52.23928523,2," +
            "eb:7a:2f:d5:b9:16,6.855976525,52.23940322,2," +
            "d6:4b:84:cd:bd:2e,6.855236387,52.23927842,4," +
            "ff:34:f5:02:f7:67,6.856007545,52.23954657,2," +
            "d9:e4:36:7c:5d:28,6.855230634,52.23919202,3," +
            "d1:80:e2:fd:8c:d0,6.85531343,52.239184,3," +
            "fe:83:67:a2:69:bc,6.855256094,52.23945667,3," +
            "ca:f2:cf:cb:4b:82,6.855701804,52.2392421,3," +
            "e3:e1:0a:56:64:4a,6.855336687,52.23936353,2," +
            "d8:b8:6c:20:f0:f2,6.855576806,52.23951718,1," +
            "c6:e9:79:6f:16:c3,6.855450978,52.23960326,2," +
            "ee:de:39:83:30:08,6.855365255,52.23963389,2," +
            "ea:cf:8a:70:49:a5,6.855344304,52.23945211,2," +
            "fe:90:05:c1:03:43,6.855590047,52.23903562,2," +
            "f1:ae:d6:bb:5f:9b,6.855854564,52.23939291,2," +
            "c9:b2:f2:97:9f:0d,6.855888626,52.23942863,2," +
            "f5:a9:02:34:cb:74,6.855224541,52.2391893,4," +
            "cf:58:04:2d:d1:c9,6.855311172,52.23918314,4," +
            "d6:41:46:07:c2:b2,6.855358643,52.23954294,4," +
            "ca:65:d0:97:14:bf,6.855356771,52.2394528,4," +
            "ee:b7:c5:f3:13:0c,6.855915101,52.23945311,4," +
            "db:27:6c:f9:cf:0f,6.855626824,52.23916345,5," +
            "da:02:14:08:8a:c7,6.856005683,52.23944166,5," +
            "df:4d:01:c0:f4:5a,6.855846101,52.23929147,5," +
            "d5:3c:fc:e1:a7:1f,6.855932105,52.23936604,5," +
            "f6:99:df:bf:2b:fa,6.856006716,52.23944404,3," +
            "e4:6b:61:bf:49:e9,6.855624144,52.23905857,5," +
            "cc:4b:b0:88:b3:5a,6.855834224,52.23937281,5," +
            "e8:86:28:b3:db:34,6.85606723,52.23960458,5," +
            "d5:b6:ac:b5:75:32,6.856064784,52.23950122,4," +
            "e2:f4:31:f6:c4:fd,6.855334929,52.23936402,4," +
            "c3:05:36:1c:5f:c6,6.855753036,52.23929059,4," +
            "d3:38:8b:43:df:2d,6.855607806,52.23914581,4," +
            "de:fe:65:0c:ae:c4,6.855369558,52.23963489,5," +
            "ca:e4:01:d7:dd:9a,6.855251263,52.23940304,5," +
            "ce:a5:5f:fa:7a:ca,6.855270144,52.23955256,5," +
            "c7:2c:9b:8c:24:0b,6.855280617,52.23963719,5," +
            "fe:1b:1a:42:1c:49,6.855757672,52.2392956,5," +
            "d3:85:d9:c0:82:31,6.855698845,52.23913623,5," +
            "d3:b4:6d:08:db:64,6.855358054,52.23958685,3," +
            "fb:f1:58:bc:c7:af,6.855699147,52.23913665,3," +
            "de:eb:c8:69:b6:48,6.855698636,52.23915367,3," +
            "d5:90:35:d1:b3:19,6.855277488,52.23961322,2," +
            "d8:69:23:a7:13:db,6.855816958,52.23946227,1," +
            "c6:e0:31:d2:6a:6f,6.855267805,52.23945533,5," +
            "db:b6:5c:8f:06:7a,6.855348347,52.23954972,5," +
            "e5:f8:b9:10:26:a8,6.855676835,52.23921716,5," +
            "ee:77:2f:7b:81:00,6.855987708,52.23952868,4," +
            "de:cf:fc:9a:a0:3a,6.856082363,52.23951812,5," +
            "f1:f3:03:11:5a:70,6.855779893,52.23921348,4," +
            "f8:62:6e:0f:54:80,6.855985399,52.2394229,4," +
            "f8:33:56:ec:2c:5b,6.855539991,52.23908163,1," +
            "d0:0b:55:15:84:ba,6.855774428,52.23946894,2," +
            "f5:45:99:07:14:50,6.85536759,52.23963223,3," +
            "cc:a0:f2:a3:a0:96,6.855612007,52.2394388,3," +
            "f4:4e:3b:cb:f7:d3,6.855357429,52.23954112,3," +
            "d7:3a:c4:28:15:65,6.855252074,52.23943468,1," +
            "e5:02:1e:97:b5:a5,6.855231655,52.23927266,1," +
            "c2:bb:d0:7b:8a:c0,6.855277898,52.2396185,1," +
            "df:8a:0a:da:ed:cd,6.855266541,52.23952539,1," +
            "f7:9a:0a:c7:81:f3,6.855248025,52.23937208,1," +
            "fc:87:f9:5c:a6:7a,6.855233488,52.23918745,1," +
            "cb:83:0a:52:58:ea,6.855361824,52.23957712,1," +
            "c1:97:69:5a:79:00,6.85531037,52.2391836,1," +
            "db:17:c4:c3:44:25,6.855705084,52.23913702,1," +
            "e6:a6:c7:ba:20:fa,6.856040121,52.2394787,1," +
            "ff:cb:c7:39:2c:a9,6.855615865,52.23905139,1," +
            "f6:7d:4d:f1:50:65,6.855367633,52.23962562,1," +
            "e0:3e:6a:22:5e:83,6.855774874,52.23931886,1," +
            "d7:2d:a5:d6:e8:ab,6.85562163,52.23916642,1," +
            "dd:dd:20:4b:08:1d,6.856045132,52.23958439,1," +
            "fe:14:99:52:ba:e8,6.855853079,52.23939471,1," +
            "d0:80:83:44:ff:ee,6.856119478,52.23955824,1," +
            "c6:8f:60:6c:a6:a6,6.855703189,52.23924522,1," +
            "f9:dc:89:d4:7a:11,6.855780432,52.23921807,1," +
            "ed:d9:6c:c6:38:f1,6.855776231,52.23921293,3," +
            "d1:d3:ab:f3:8e:63,6.855949643,52.23948978,1,");
    private HashMap<String, ArrayList<Float>> locationOfBeacon;
    private ArrayList<Float> coordinates;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//

    //        beaconManager = BeaconManager.getInstanceForApplication(this);
//        TextView tv=findViewById(R.id.textID);
//        tv.setText("code ran up to here");
//        beaconManager.getBeaconParsers().add(new BeaconParser().
//                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
//        beaconManager.addMonitorNotifier(new MonitorNotifier() {
//            @Override
//            public void didEnterRegion(Region region) {
//                Log.i(TAG, "I just saw a beacon for the first time!");
//                tv.setText("I just saw a beacon for the first time");
//            }
//
//            @Override
//            public void didExitRegion(Region region) {
//                Log.i(TAG, "I no longer see a beacon");
//                tv.setText("I no longer see a beacon");
//            }
//
//            @Override
//            public void didDetermineStateForRegion(int state, Region region) {
//                Log.i(TAG, "I have just switched from seeing/not seeing beacons: "+state);
//                tv.setText("I have just switched from seeing/not seeing beacons");
//            }
//        });
//
//        beaconManager.startMonitoring(new Region("myMonitoringUniqueId", null, null, null));
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readFromCsv();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
        beaconManager.addRangeNotifier(new RangeNotifier() {

            Set data= new HashSet();
            List adapter = new ArrayList();
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                System.out.println("It goes into didRangeBeaconsInRegion");
                if (beacons.size() > 0) {

                    System.out.println("beacon size is " + beacons.size());
                    data.clear();
                    for (Beacon beacon : beacons) {
                        String MAC= beacon.getBluetoothAddress().toLowerCase(Locale.ROOT);
                        data.add("MAC: "+ beacon.getBluetoothAddress()
                                + "\nDISTANCE: " + beacon.getDistance() + "\n");
                        ArrayList <Float> map_val=locationOfBeacon.get(MAC);
                        System.out.println(MAC);
                        if (locationOfBeacon.containsKey(MAC)){
                            if (map_val.size()==3)
                                map_val.add((float) beacon.getDistance());
                            else map_val.set(3,(float) beacon.getDistance());}
                        else System.out.println("Found a beacon that's not in the list");


                    }
                    updateList();
                }

            }
            public void updateList(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        adapter.add(data);
                        System.out.println(data);
                        System.out.println("Something happened in run");

                    }
                });}

        });

        beaconManager.startRangingBeacons(new Region("myRangingUniqueId", null, null, null));
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }


        }
    }

    void readFromCsv(){
        String[] parts=file.split(",");
        locationOfBeacon=new HashMap<>();
        int i=0;
        while (i<parts.length){
            coordinates=new ArrayList<Float>();
            coordinates.add(Float.parseFloat(parts[i+1]));
            coordinates.add(Float.parseFloat(parts[i+2]));
            coordinates.add(Float.parseFloat(parts[i+3]));
            locationOfBeacon.put(parts[i],coordinates);
            i+=4;
        }
        System.out.println(locationOfBeacon.keySet());

    }

}