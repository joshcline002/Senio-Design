// MPU-6000 Short Example Sketch
// By Arduino User JohnChi
// August 17, 2014
// Public Domain
#include<Wire.h>
int16_t AcX,AcY,AcZ,Tmp,GyX,GyY,GyZ;
void I2Csetup(int MPU){
  Wire.begin();
  
  Wire.beginTransmission(MPU);
  Wire.write(0x6B);  // PWR_MGMT_1 register
  Wire.write(0);     // set to zero (wakes up the MPU-6050)
  Wire.endTransmission(true);
  
  Wire.beginTransmission(MPU);
  Wire.write(0x1C);  // Accel config register
  Wire.write(0x08);     // Setting range to +-4G
  Wire.endTransmission(true);
  
  Wire.beginTransmission(MPU);
  Wire.write(0x1B);  // Gyro config register
  Wire.write(0x08);     // setting range to +-500 degrees per second
  Wire.endTransmission(true);
}
void I2Cprint(int MPU){
  Wire.beginTransmission(MPU);
  Wire.write(0x3B);  // starting with register 0x3B (ACCEL_XOUT_H)
  Wire.endTransmission(false);
  Wire.requestFrom(MPU,14,true);  // request a total of 14 registers
  AcX=Wire.read()<<8|Wire.read();  // 0x3B (ACCEL_XOUT_H) & 0x3C (ACCEL_XOUT_L)    
  AcY=Wire.read()<<8|Wire.read();  // 0x3D (ACCEL_YOUT_H) & 0x3E (ACCEL_YOUT_L)
  AcZ=Wire.read()<<8|Wire.read();  // 0x3F (ACCEL_ZOUT_H) & 0x40 (ACCEL_ZOUT_L)
  Tmp=Wire.read()<<8|Wire.read();  // 0x41 (TEMP_OUT_H) & 0x42 (TEMP_OUT_L)
  GyX=Wire.read()<<8|Wire.read();  // 0x43 (GYRO_XOUT_H) & 0x44 (GYRO_XOUT_L)
  GyY=Wire.read()<<8|Wire.read();  // 0x45 (GYRO_YOUT_H) & 0x46 (GYRO_YOUT_L)
  GyZ=Wire.read()<<8|Wire.read();  // 0x47 (GYRO_ZOUT_H) & 0x48 (GYRO_ZOUT_L)
  Serial.print("AcX = "); Serial.print(AcX/8192.0);
  Serial.print(" | AcY = "); Serial.print(AcY/8192.0);
  Serial.print(" | AcZ = "); Serial.print(AcZ/8192.0);
  Serial.print(" | Tmp = "); Serial.print((Tmp/340.00+36.53)*9/5 + 32);  //equation for temperature in degrees C from datasheet
  Serial.print(" | GyX = "); Serial.print(GyX/65.5);
  Serial.print(" | GyY = "); Serial.print(GyY/65.5);
  Serial.print(" | GyZ = "); Serial.print(GyZ/65.5);
  delay(200);
}
