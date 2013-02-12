/* **************************************************
 Copyright (c) 2012, University of Cambridge
 Neal Lathia, neal.lathia@cl.cam.ac.uk

This library was developed as part of the EPSRC Ubhave (Ubiquitous and
Social Computing for Positive Behaviour Change) Project. For more
information, please visit http://www.emotionsense.org

Permission to use, copy, modify, and/or distribute this software for any
purpose with or without fee is hereby granted, provided that the above
copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 ************************************************** */

package com.ubhave.dataformatter.json.push;

import org.json.simple.JSONObject;

import com.ubhave.dataformatter.json.PushSensorJSONFormatter;
import com.ubhave.sensormanager.data.SensorData;
import com.ubhave.sensormanager.data.pushsensor.ScreenData;

public class ScreenFormatter extends PushSensorJSONFormatter
{
	private final static String STATUS = "status";

	private String getScreenStatusString(ScreenData data)
	{
		if (data.isOn())
		{
			return "SCREEN_ON";
		}
		else if (data.isOff())
		{
			return "SCREEN_OFF";
		}
		else
		{
			return "UNKNOWN";
		}
	}
	
	private int getScreenStatusId(String status)
	{
		if (status.equals("SCREEN_ON"))
		{
			return ScreenData.SCREEN_ON;
		}
		else if (status.equals("SCREEN_OFF"))
		{
			return ScreenData.SCREEN_OFF;
		}
		else
		{
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void addSensorSpecificData(JSONObject json, SensorData data)
	{
		ScreenData screenData = (ScreenData) data;
		json.put(STATUS, getScreenStatusString(screenData));
	}
	
	@Override
	public SensorData toSensorData(String jsonString)
	{
		JSONObject jsonData = parseData(jsonString);
		if (jsonData != null)
		{
			long timestamp = super.parseTimeStamp(jsonData);
			int screenStatus = getScreenStatusId((String) jsonData.get(STATUS));
			return new ScreenData(timestamp, screenStatus, null);
		}
		else return null;
	}
}
