
//#define CS1 6  //arbitrary chip select pin
#define IMU1 104
#define IMU2 105

void setup()
{
  
  //SPIsetup(CS1);
  I2Csetup(IMU2);
  //Bluetoothsetup();
  Serial1.begin(9600);
  //Serial1.print("$");
  //Serial1.print("$");
  //Serial1.print("$");
  //delay(100);
  //Serial1.println("U,9600,N");
  //Serial1.println("SN, Posture");
  //Serial1.begin(9600);
  //Serial.begin(9600);
  
}

void loop()
{
  Serial1.print("EMG1 : ");
  EMGprint(A0);
  Serial1.print(" IMU1 : ");
  I2Cprint(IMU2);
  Serial1.println(";");
}
