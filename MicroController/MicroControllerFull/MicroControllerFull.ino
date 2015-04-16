#define CS1 6  //arbitrary chip select pin

void setup()
{
  SPIsetup(CS1);
  Bluetoothsetup();
  Serial.begin(9600);
}

void loop()
{
  Serial.print("EMG1 : ");
  EMGprint(A0);
  Serial.print("IMU1 : ");
  SPIprint(CS1);
}
