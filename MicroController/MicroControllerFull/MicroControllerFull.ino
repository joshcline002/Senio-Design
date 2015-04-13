// variables for input pins and
int num_analog_in = 1;
int analogInput[1];
// variable to store the value 
int value[1]; 

void setup()
{
  // declaration of pin modes
  for(int i=0;i<num_analog_in;i++)
  {
    analogInput[i] = 17+1;
    value[i] = 0;     
    pinMode(analogInput[i], INPUT);    
  }
  
  // begin sending over serial port
  Serial.begin(9600);
  Serial1.begin(9600);
}

void loop()
{
  // read the value on analog input
  for(int i=0;i<num_analog_in;i++)
  {
    value[i] = analogRead(analogInput[i]);
  }

  // print out value over the serial port
  for(int i=0;i<num_analog_in;i++)
  {
    Serial.println(10000 + i + 1); //prefix
    Serial1.println(1000 + i + 1);
    Serial.println(value[i]);
    Serial1.println(value[i]);
    Serial.println(10010); //end signal
    Serial1.println(10010); //end signal
  }
  // wait for a bit to not overload the port
  delay(10);
}

