/**
*  This file contains the source code to initialize the ADC and 
*  continuously poll data from ADC0. The values recorded will 
*  then be printed to stdout.
*/
#include <stdio.h>

void ADCInit();
int getADC();

void ADCInit()
{
  ADMUX = 0xC0; //choose A0 as input pin, use Internal 2.56V reference
  ADCSRA = 0x86; //Interrupt disabled, Prescaler of 64, ADC clk rate of 125 kHz
  ADCSRB = 0x00; //no auto trigger, not high speed mode
}

int getADC()
{
  int result = 0;
  ADCSRA |= 0x40; //start conversion
  while(ADCSRA & 0x10); //while conversion not done, block
  ADCSRA |= 0x10; //Clear interrupt flag by writing 1 to its bit
  
  //Get result
  result = ADCH;
  result = (result << 8) | ADCL;
  return result;
}
