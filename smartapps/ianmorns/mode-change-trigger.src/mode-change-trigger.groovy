/**
 *  Mode Change Trigger
 *
 *  Copyright 2017 Ian Morns
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Mode Change Trigger",
    namespace: "ianmorns",
    author: "Ian Morns",
    description: "Change location mode based on switches turning on/off or time of day",
    category: "Mode Magic",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/ModeMagic/rise-and-shine.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/ModeMagic/rise-and-shine@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/ModeMagic/rise-and-shine@2x.png")


preferences {
	section("Activate this mode") {
		input "newMode", "mode", title: "select a mode", multiple: false, required: true
	}
    
    section("When one of these switches..."){
    	input "switchesOn", "capability.Switch", title: "turns on", multiple: true, required: false
        input "switchesOff", "capability.Switch", title: "turns off", multiple: true, required: false
    }
    
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(switchesOn, "switch.on", switchHandler)
	subscribe(switchesOff, "switch.off", switchHandler)
}

def switchHandler(evt)
{
	log.debug "Changing mode from ${location.currentMode} to $newMode"
	location.setMode(newMode)
}