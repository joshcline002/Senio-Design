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
    analogInput[i] = 17+1+i;
    value[i] = 0;     
    pinMode(18, INPUT);    
  }
  //Serial1.begin(115200);  // The Bluetooth Mate defaults to 115200bps
  //Serial1.print("$");  // Print three times individually
  //Serial1.print("$");
  //Serial1.print("$");  // Enter command mode
  //delay(100);  // Short delay, wait for the Mate to send back CMD
  //Serial1.println("U,9600,N");  // Temporarily Change the baudrate to 9600, no parity
  // 115200 can be too fast at times for NewSoftSerial to relay the data reliably
  // begin sending over serial port
  Serial.begin(9600);
  //Serial1.begin(9600);
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
    //Serial.println(10000 + i + 1); //prefix
    //Serial1.println(1000 + i + 1);
    
    Serial.println(analogRead(19));
    //Serial1.println(value[i]);
    
    //Serial.println(10010); //end signal
    //Serial1.println(10010); //end signal
  }
  // wait for a bit to not overload the port
  delay(100);
}

