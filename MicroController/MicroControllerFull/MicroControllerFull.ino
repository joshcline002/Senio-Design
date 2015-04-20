//#define CS1 6  //arbitrary chip select pin
#define IMU1 104
#define IMU2 105
void setup()
{
  //SPIsetup(CS1);
  I2Csetup(IMU2);
 //Bluetoothsetup();
  Serial.begin(9600);
}

void loop()
{
  Serial.print("EMG1 : ");
  EMGprint(A0);
  Serial.print(" IMU1 : ");
  I2Cprint(IMU2);
  Serial.println(" ");
}
