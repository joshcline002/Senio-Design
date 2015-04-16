#include <SPI.h>

/** This is the code for configuring the IMUs **/

#define GYRO_CONFIG   0x1B
#define ACCEL_CONFIG 0x1C
#define ACCEL_X_H     0x3B
#define ACCEL_X_L     0x3C
#define ACCEL_Y_H     0x3D
#define ACCEL_Y_L     0x3E
#define ACCEL_Z_H     0x3F
#define ACCEL_Z_L     0x40
#define GYRO_X_H      0x43
#define GYRO_X_L      0x44
#define GYRO_Y_H      0x45
#define GYRO_Y_L      0x46
#define GYRO_Z_H      0x47
#define GYRO_Z_L      0x48

#define CS1 6  //arbitrary chip select pin

void SPIsetup(){
  SPI.setClockDivider(SPI_CLOCK_DIV8); //SPI clock at 1Mhz
  SPI.setBitOrder(MSBFIRST);
  SPI.setDataMode(SPI_MODE0); //not sure about this line, arduino is not very clear
  SPI.begin();
  pinMode(CS1, OUTPUT);
  digitalWrite(CS1, HIGH); //deselect the chip
  configIMU(CS1);
  Serial.begin(9600);
}

void SPIprint()
{
  //Get X-axis data
  int X_axis = readIMUAxis(ACCEL_X_H, ACCEL_X_L, CS1);
  Serial.print("X-axis Data:");
  Serial.println(X_axis);
}

int readIMUAxis(int high_address, int low_address, int CS)
{
  int result = 0;
  digitalWrite(CS, LOW); //pull slave_select low it begin transmission
  SPI.transfer(0x10 | high_address); //ored with 0x10 to indicate a read of the high address
  result = SPI.transfer(0x10 | low_address); //get high address data, request low address data
  result = result << 8; //shift high data left 8
  result = result + SPI.transfer(0x10| low_address); //get low address data, send some data to complete transaction
  delay(1);
  digitalWrite(CS, HIGH);
  return result;
}

void writeIMUReg(int reg_addr, char data, int CS)
{
  digitalWrite(CS, LOW);
  SPI.transfer(reg_addr);
  SPI.transfer(data);
  delay(1);
  digitalWrite(CS, HIGH);
}

void configIMU(int CS)
{
  writeIMUReg(0x1A, 0x00, CS);
  writeIMUReg(0x19, 0x07, CS); //scale Sample clock by 8, frequency will be 1 Khz
  writeIMUReg(GYRO_CONFIG, 0x18, CS); //Select full +2000 dps to -2000 dps for gyro
  writeIMUReg(ACCEL_CONFIG, 0x18, CS); //Select full +16 g to -16 g for Accel
}
