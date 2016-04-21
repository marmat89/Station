
/*
# This sample codes is for testing the pH meter V1.0.
 # Editor : YouYou
 # Date   : 2013.10.12
 # Ver    : 0.1
 # Product: pH meter
 # SKU    : SEN0161
*/

#define SensorPin 0          //pH meter Analog output to Arduino Analog Input 0
unsigned long int avgValue;  //Store the average value of the sensor feedback
float b;
int buf[10],temp;

char recv; // byte received on the serial port
String inputString = "";         // a string to hold incoming data
boolean stringComplete = false;  // whether the string is complete



int pin = 8;
unsigned long sampletime_ms = 10000;
unsigned long duration;
unsigned long starttime;
unsigned long lowpulseoccupancy = 0;
  

float dustDensity05=0;

float dustDensity1=0;

float dustDensity25=0;

//COUNTER : count of succes and error 
int errorCount=0;
int successCount=0;


void setup() {
  Serial.begin(9600);
  pinMode(9,INPUT);
  starttime = millis();
}

void loop()
{
  
  dustDensity05=getPM(8);
  dustDensity1=getPM(9);
  dustDensity25=getPM(11);
}

float getPH()
{
   for(int i=0;i<10;i++)       //Get 10 sample value from the sensor for smooth the value
  { 
    buf[i]=analogRead(SensorPin);
    delay(10);
  }
  for(int i=0;i<9;i++)        //sort the analog from small to large
  {
    for(int j=i+1;j<10;j++)
    {
      if(buf[i]>buf[j])
      {
        temp=buf[i];
        buf[i]=buf[j];
        buf[j]=temp;
      }
    }
  }
  avgValue=0;
  for(int i=2;i<8;i++)                      //take the average value of 6 center sample
    avgValue+=buf[i];
  float phValue=(float)avgValue*5.0/1024/6; //convert the analog into millivolt
  phValue=3.5*phValue;                      //convert the millivolt into pH value
  return (float)phValue;
}



float getPM(int pm){
  float calcVoltage = 0;
  float ratio = 0;
  float dustDensity=0;
  float dustDensityMG=0;
  while (dustDensity==0){
  duration = pulseIn(pm, LOW);
  lowpulseoccupancy = lowpulseoccupancy+duration;
  if ((millis()-starttime) > sampletime_ms )
    {
      ratio = lowpulseoccupancy/((millis()-starttime)*10.0);  // Integer percentage 0=>100
      dustDensity = 1.1*pow(ratio,3)-3.8*pow(ratio,2)+520*ratio+0.62; // using spec sheet curve
       /* convert into concentration in particles per 0.01 cft */
      //dustDensity = 1.438e5 * pow(ratio, 2.0) + 4.488e4 * ratio;
      calcVoltage=(dustDensity/120000)+0.0256;
      dustDensityMG = (0.172 * calcVoltage - 0.00999);
      if (ratio>0)
        {
        successCount++;
      }else{
        errorCount++;
      }
      lowpulseoccupancy = 0;
      starttime = millis();
    }
  }
  return (float) dustDensity;
    
  }


void serialEvent() {
  while (Serial.available()) {
    // get the new byte:
    char inChar = (char)Serial.read(); 
    // add it to the inputString:
    if (inChar == '=') {
      stringComplete = true;
      if (stringComplete) {
    // confirm values received in serial monitor window
    //Serial.println(inputString);
    if (inputString=="getPH"){
      //Serial.println("--Arduino exeCommand: getTemp ");
      Serial.println(getPH());
    }
    if (inputString=="getPM05"){
      //Serial.println("--Arduino exeCommand: getSpeed ");
      Serial.println(dustDensity05); 
    } 
    if (inputString=="getPM1"){
      //Serial.println("--Arduino exeCommand: getSpeed ");
      Serial.println(dustDensity1); 
    } 
    if (inputString=="getPM25"){
      //Serial.println("--Arduino exeCommand: getSpeed ");
      Serial.println(dustDensity25); 
    } 
    inputString = "";
    stringComplete = false;
  }
    } 
    else{
      inputString += inChar;
      // if the incoming character is a newline, set a flag
      // so the main loop can do something about it:
    }
  }
}


