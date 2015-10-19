#include <Wire.h>

/*
  I2C 1 uses pins 29 and 30
  I2C 2 uses pins 9 and 5
  ADC 0 uses pin 15
  Serial 1 uses pins 7 and 8
*/

#define IMU1 104
#define IMU2 105

int AcX,AcY,AcZ,Tmp,GyX,GyY,GyZ;

void setup() {
    pinMode(15, INPUT_ANALOG);
    pinMode(BOARD_LED_PIN, OUTPUT);
    I2Csetup(IMU1, 30, 29); //set SDA as pin 29, set SCL as pin 30
    I2Csetup(IMU2, 30, 29); 
    //I2CSetup(IMU1, 5, 9); //set SDA as pin 9, set SCL as pin 5
    //I2CSetup(IMU2, 5, 9);
    BTsetup();
}

void loop() {
  int sensorValue = analogRead(15);
  Serial1.print("EMG: ");
  Serial1.println(sensorValue);
}

void I2Csetup(int MPU, int clockPin, int dataPin){
  Wire.begin(clockPin, dataPin);
  
  Wire.beginTransmission(MPU);
  Wire.send(0x6B);  // PWR_MGMT_1 register
  Wire.send(0);     // set to zero (wakes up the MPU-6050)
  Wire.endTransmission();
  
  Wire.beginTransmission(MPU);
  Wire.send(0x1C);  // Accel config register
  Wire.send(0x08);     // Setting range to +-4G
  Wire.endTransmission();
  
  Wire.beginTransmission(MPU);
  Wire.send(0x1B);  // Gyro config register
  Wire.send(0x08);     // setting range to +-500 degrees per second
  Wire.endTransmission();
}

int I2Cread(int MPU, int clockPin, int dataPin)
{
  Wire.begin(clockPin, dataPin);
  Wire.send(0x3B);  // starting with register 0x3B (ACCEL_XOUT_H)
  Wire.endTransmission();
  Wire.requestFrom(MPU,14);  // request a total of 14 registers
  AcX=Wire.receive()<<8|Wire.receive();  // 0x3B (ACCEL_XOUT_H) & 0x3C (ACCEL_XOUT_L)    
  AcY=Wire.receive()<<8|Wire.receive();  // 0x3D (ACCEL_YOUT_H) & 0x3E (ACCEL_YOUT_L)
  AcZ=Wire.receive()<<8|Wire.receive();  // 0x3F (ACCEL_ZOUT_H) & 0x40 (ACCEL_ZOUT_L)
  Tmp=Wire.receive()<<8|Wire.receive();  // 0x41 (TEMP_OUT_H) & 0x42 (TEMP_OUT_L)
  GyX=Wire.receive()<<8|Wire.receive();  // 0x43 (GYRO_XOUT_H) & 0x44 (GYRO_XOUT_L)
  GyY=Wire.receive()<<8|Wire.receive();  // 0x45 (GYRO_YOUT_H) & 0x46 (GYRO_YOUT_L)
  GyZ=Wire.receive()<<8|Wire.receive();  // 0x47 (GYRO_ZOUT_H) & 0x48 (GYRO_ZOUT_L)
}

void BTsetup()
{
  Serial1.begin(115200);
  Serial1.print("$");
  Serial1.print("$");
  Serial1.print("$");
  delay(100);
  Serial1.println("U,9600,N");
  Serial1.begin(9600);
}
