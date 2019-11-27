package com.example.hueapp.TestingHelpers;

import com.example.hueapp.Model.HueNetwork;

import org.json.JSONException;
import org.json.JSONObject;

public class HueNetworkTestHelper {


    public static HueNetwork LucasLocalTestNetworkFilled = getTestNetworkFilled();
    public static HueNetwork LucasLocalTextNetworkEmpty = getTestNetworkEmpty();

    private static HueNetwork getTestNetworkEmpty() {
        return new HueNetwork("145.49.38.213");
    }

    private static HueNetwork getTestNetworkFilled() {
        try {

            HueNetwork hueNetwork = new HueNetwork("145.49.38.213", "89ca8e653a5e6abef8e02c403ebcf8a", new JSONObject("{\"lights\":{\"1\":{\"state\":{\"on\":true,\"bri\":254,\"hue\":4444,\"sat\":254,\"xy\":[0.0,0.0],\"ct\":0,\"alert\":\"none\",\"effect\":\"none\",\"colormode\":\"hs\",\"reachable\":true},\"type\":\"Extended color light\",\"name\":\"Hue Lamp 1\",\"modelid\":\"LCT001\",\"swversion\":\"65003148\",\"uniqueid\":\"00:17:88:01:00:d4:12:08-0a\",\"pointsymbol\":{\"1\":\"none\",\"2\":\"none\",\"3\":\"none\",\"4\":\"none\",\"5\":\"none\",\"6\":\"none\",\"7\":\"none\",\"8\":\"none\"}},\"2\":{\"state\":{\"on\":true,\"bri\":254,\"hue\":23536,\"sat\":144,\"xy\":[0.346,0.3568],\"ct\":201,\"alert\":\"none\",\"effect\":\"none\",\"colormode\":\"hs\",\"reachable\":true},\"type\":\"Extended color light\",\"name\":\"Hue Lamp 2\",\"modelid\":\"LCT001\",\"swversion\":\"65003148\",\"uniqueid\":\"00:17:88:01:00:d4:12:08-0b\",\"pointsymbol\":{\"1\":\"none\",\"2\":\"none\",\"3\":\"none\",\"4\":\"none\",\"5\":\"none\",\"6\":\"none\",\"7\":\"none\",\"8\":\"none\"}},\"3\":{\"state\":{\"on\":true,\"bri\":254,\"hue\":65136,\"sat\":254,\"xy\":[0.346,0.3568],\"ct\":201,\"alert\":\"none\",\"effect\":\"none\",\"colormode\":\"hs\",\"reachable\":true},\"type\":\"Extended color light\",\"name\":\"Hue Lamp 3\",\"modelid\":\"LCT001\",\"swversion\":\"65003148\",\"uniqueid\":\"00:17:88:01:00:d4:12:08-0c\",\"pointsymbol\":{\"1\":\"none\",\"2\":\"none\",\"3\":\"none\",\"4\":\"none\",\"5\":\"none\",\"6\":\"none\",\"7\":\"none\",\"8\":\"none\"}},\"4\":{\"state\":{\"on\":true,\"bri\":254,\"hue\":5000,\"sat\":254,\"xy\":[0.0,0.0],\"ct\":0,\"alert\":\"none\",\"effect\":\"none\",\"colormode\":\"xy\",\"reachable\":true},\"type\":\"Extended color light\",\"name\":\"New Light - 4\",\"modelid\":\"LCT001\",\"swversion\":\"65003148\",\"uniqueid\":\"bd:a2:e8:6e:7b:1d:68:19-db\",\"pointsymbol\":{\"1\":\"none\",\"2\":\"none\",\"3\":\"none\",\"4\":\"none\",\"5\":\"none\",\"6\":\"none\",\"7\":\"none\",\"8\":\"none\"}},\"5\":{\"state\":{\"on\":true,\"bri\":254,\"hue\":null,\"sat\":254,\"xy\":null,\"ct\":null,\"alert\":\"none\",\"effect\":\"none\",\"colormode\":null,\"reachable\":true},\"type\":\"Dimmable light\",\"name\":\"New white Light - 5\",\"modelid\":\"LWB004\",\"swversion\":\"65003148\",\"uniqueid\":\"1e:2d:3d:e8:3d:a4:45:59-3b\",\"pointsymbol\":{\"1\":\"none\",\"2\":\"none\",\"3\":\"none\",\"4\":\"none\",\"5\":\"none\",\"6\":\"none\",\"7\":\"none\",\"8\":\"none\"}}},\"schedules\":{\"1\":{\"time\":\"2012-10-29T12:00:00\",\"description\":\"\",\"name\":\"schedule\",\"command\":{\"body\":{\"scene\":null,\"on\":true,\"xy\":null,\"bri\":null,\"transitiontime\":null},\"address\":\"/api/newdeveloper/groups/0/action\",\"method\":\"PUT\"}}},\"config\":{\"portalservices\":false,\"gateway\":\"192.168.2.1\",\"mac\":\"00:00:88:00:bb:ee\",\"bridgeid\":\"000088FFFE00BBEE\",\"modelid\":\"BSB001\",\"swversion\":\"01005215\",\"linkbutton\":false,\"ipaddress\":\"169.254.20.147:80\",\"proxyport\":0,\"swupdate\":{\"text\":\"\",\"notify\":false,\"updatestate\":0,\"url\":\"\"},\"netmask\":\"255.255.255.0\",\"name\":\"Philips hue\",\"dhcp\":true,\"proxyaddress\":\"\",\"whitelist\":{\"newdeveloper\":{\"name\":\"test user\",\"last use date\":\"2012-10-29T12:00:00\",\"create date\":\"2012-10-29T12:00:00\"},\"1e9826c1e130642446bc51aaf959694\":{\"name\":\"Lucas\",\"last use date\":\"2019-11-18T18:00:48\",\"create date\":\"2019-11-18T17:59:19\"}},\"UTC\":\"2012-10-29T12:05:00\"},\"groups\":{\"1\":{\"name\":\"Group 1\",\"action\":{\"on\":true,\"bri\":254,\"hue\":33536,\"sat\":144,\"xy\":[0.346,0.3568],\"ct\":201,\"alert\":null,\"effect\":\"none\",\"colormode\":\"xy\",\"reachable\":null},\"lights\":[\"1\",\"2\"]}},\"scenes\":{}}"));
            return hueNetwork;
//"http://145.49.38.213/api/89ca8e653a5e6abef8e02c403ebcf8a"
            } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
