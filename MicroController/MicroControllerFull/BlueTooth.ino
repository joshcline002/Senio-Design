void Bluetoothsetup(){
  Serial1.begin(115200);
  Serial1.print("$");
  Serial1.print("$");
  Serial1.print("$");
  delay(100);
  Serial1.println("U,9600,N");
  Serial1.begin(9600);
}
 
