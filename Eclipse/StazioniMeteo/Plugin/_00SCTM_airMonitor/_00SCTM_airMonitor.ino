#include <OneWire.h>
#include <dht11.h>
dht11 DHT;
#define DHT11_PIN 4 

char recv; // byte received on the serial port
String inputString = "";         // a string to hold incoming data
boolean stringComplete = false;  // whether the string is complete

int rpmcount;
float rpm;
long timeold;

//temp VAR
int DS18B20_Pin =4;
OneWire ds(DS18B20_Pin);

int rainPin = A0;    // select the input pin rainSensor
int lightPin = A1;    // select the input pin LighSensor
int rainvalue = 0;  // variable to store the value coming from the sensor
int lightvalue=0;// variable to store the value coming from the sensor

void setup() {
  Serial.begin(9600);
  //Interrupt 0 is digital pin 2, so that is where the IR detector is connected
  //Triggers on FALLING (change from HIGH to LOW)
  attachInterrupt(0, rpm_fun, CHANGE);
  rpmcount = 0;
  rpm = 0;
  timeold = 0;
}

void loop()
{
  if (stringComplete) {
    // confirm values received in serial monitor window
    //Serial.println(inputString);
    if (inputString=="getTemp"){
      //Serial.println("--Arduino exeCommand: getTemp ");
      Serial.println(getTemp());
    }
    if (inputString=="getSpeed"){
      //Serial.println("--Arduino exeCommand: getSpeed ");
      Serial.println(getSpeed()); 
    } 
    if (inputString=="getHumidity"){
      //Serial.println("--Arduino exeCommand: getHumidity ");
      Serial.println(getHumidity()); 
    }
    if (inputString=="getRain"){
      //Serial.println("--Arduino exeCommand: getRain ");
      Serial.println(getRain()); 
    }   
    if (inputString=="getLight"){
      //Serial.println("--Arduino exeCommand: getLight ");
      Serial.println(getLight()); 
    }
    inputString = "";
    stringComplete = false;
  }
}

float getSpeed()
{
  //Don't process interrupts during calculations
  detachInterrupt(0);
  //Note that this would be 60*1000/(millis() - timeold)*rpmcount if the interrupt
  //happened once per revolution instead of twice. Other multiples could be used
  //for multi-bladed propellers or fans
  float giri=(float)rpmcount/20;
  rpm =(float)((60000/(millis() - timeold))*giri);
  //Serial.println("RPM=");
  //Serial.println((float)rpm);
  //rpm =(int) 60*1000/(millis() - timeold)*(rpmcount/2);
  timeold = millis();
  rpmcount = 0;
  //Restart the interrupt processing
  attachInterrupt(0, rpm_fun, CHANGE);
  return (float)rpm;
}

void rpm_fun()
{
  //Each rotation, this interrupt function is run twice, so take that into consideration for 
  //calculating RPM
  //Update count
  rpmcount++;
}

int getTemp(){
  //Serial.println("Type,\tstatus,\tHumidity (%),\tTemperature (C)");
  int chk;
  //Serial.print("DHT11, \t");
  chk = DHT.read(DHT11_PIN);    // READ DATA
  switch (chk){
    case DHTLIB_OK:  
                //Serial.print("OK,\t"); 
                break;
    case DHTLIB_ERROR_CHECKSUM: 
                Serial.print("Checksum error,\t"); 
                break;
    case DHTLIB_ERROR_TIMEOUT: 
                Serial.print("Time out error,\t"); 
                break;
    default: 
                Serial.print("Unknown error,\t"); 
                break;
  }

 return(DHT.temperature);
}
int getHumidity(){
  //Serial.println("Type,\tstatus,\tHumidity (%),\tTemperature (C)");
  int chk;
  //Serial.print("DHT11, \t");
  chk = DHT.read(DHT11_PIN);    // READ DATA

  // DISPLAT DATA
  //Serial.print(DHT.humidity,1);
  //Serial.print(",\t");
  //Serial.println(DHT.temperature,1);
  return (int)DHT.humidity;
}
int getRain() {
  rainvalue = analogRead(rainPin);    
  //Serial.print("RAIN VALUE = " );
  //Serial.println(sensorvalue);
  rainvalue = map(rainvalue,0, 1023, 100, 1);
  return (int)rainvalue;
}
int getLight() {
  lightvalue= analogRead(lightPin);    
  //Serial.print("LIGHT VALUE = " );
  //Serial.println(lightvalue);

  lightvalue = map(lightvalue, 0, 1023, 1, 100);
  return (int)lightvalue;
}
void serialEvent() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read(); 
    // add it to the inputString:
    if (inChar == '=') {
      stringComplete = true;
    } 
    else{
      inputString += inChar;
      // if the incoming character is a newline, set a flag
      // so the main loop can do something about it:
    }
  }
}


