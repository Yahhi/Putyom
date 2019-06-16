#include <SoftwareSerial.h>
SoftwareSerial GSMport(8, 9); // RX, TX
int char_;
int SensorPin = 2;
int SensorState;
int SensorLastState = HIGH;
// variables will change:
int buttonState = 0;         // variable for reading the pushbutton status
bool pressed = false;
int count=0;

void setup() {
  delay(3000); //дадим время на инициализацию GSM модулю
  pinMode(SensorPin, INPUT);
  digitalWrite(SensorPin, HIGH);  //вкл. подтягивающий резистор 20ом
  Serial.begin(9600);  //скорость порта
  Serial.println("GPRS test");
  GSMport.begin(9600);
  gprs_init();
}

void loop() {
 
  SensorState = digitalRead(SensorPin);
  if (SensorState == LOW) {
    if(!pressed)
      {
        pressed=true;
        
        Serial.println("send 1");
        gprs_send("lat=55.796055&lng=49.126214&type=1&SignId=3");
        delay(1000);
        gprs_send("lat=55.794761&lng=49.140090&type=2&SignId=4");
        delay(10000);
        Serial.println("send 2");
        gprs_send("lat=55.794084&lng=49.109778&type=1&SignId=5");
        delay(1000);
        gprs_send("lat=55.791194&lng=49.116417&type=2&SignId=6");
        delay(10000);
        Serial.println("send 3");
        gprs_send("lat=55.790007&lng=49.110623&type=1&SignId=7");
        delay(1000);
        gprs_send("lat=55.789160&lng=49.111873&type=2&SignId=8");
        delay(10000);
        Serial.println("send 4");
        gprs_send("lat=55.790064&lng=49.106042&type=1&SignId=9");
        delay(1000);
        gprs_send("lat=55.789517&lng=49.105178&type=2&SignId=10");
        delay(10000);
        Serial.println("send 5");
        gprs_send("lat=55.788338&lng=49.102917&type=1&SignId=11");
        delay(1000);
        gprs_send("lat=55.790568&lng=49.100209&type=2&SignId=12");
        delay(10000);
        Serial.println("send 6");
        gprs_send("lat=55.767868&lng=49.127115&type=1&SignId=13");
        delay(1000);
        gprs_send("lat=55.769381&lng=49.131508&type=2&SignId=14");
        delay(10000);
        Serial.println("send 7");
        gprs_send("lat=55.763430&lng=49.151423&type=1&SignId=15");
        delay(1000);
        gprs_send("lat=55.763228&lng=49.154751&type=2&SignId=16");
        delay(10000);
        Serial.println("send 8");
        gprs_send("lat=55.761730&lng=49.162024&type=1&SignId=17");
        delay(1000);
        gprs_send("lat=55.755780&lng=49.164100&type=2&SignId=18");
        delay(10000);
        Serial.println("send 9");
        gprs_send("lat=55.751566&lng=49.163933&type=1&SignId=19");
        delay(1000);
        gprs_send("lat=55.751732&lng=49.164566&type=2&SignId=20");
        delay(10000);
        Serial.println("send 10");
        gprs_send("lat=55.752900&lng=49.179569&type=1&SignId=21");
        delay(1000);
        gprs_send("lat=55.754092&lng=49.179523&type=2&SignId=22");
        delay(10000);
      
      }
  }
  else
  {
    pressed=false;
  }
  delay(100);// prepare for next data ..
  
}

void gprs_init() {  //Процедура начальной инициализации GSM модуля
  int d = 500;
  int ATsCount = 7;
  String ATs[] = {  //массив АТ команд
    "AT+SAPBR=3,1,\"CONTYPE\",\"GPRS\"",  //Установка настроек подключения
    "AT+SAPBR=3,1,\"APN\",\"internet.letai.ru\"",
    "AT+SAPBR=3,1,\"USER\",\"\"",
    "AT+SAPBR=3,1,\"PWD\",\"\"",
    "AT+SAPBR=1,1",  //Устанавливаем GPRS соединение
    "AT+HTTPINIT",  //Инициализация http сервиса
    "AT+HTTPPARA=\"CID\",1"  //Установка CID параметра для http сессии
  };
  int ATsDelays[] = {6, 1, 1, 1, 3, 3, 1}; //массив задержек
  Serial.println("GPRG init start");
  for (int i = 0; i < ATsCount; i++) {
    Serial.println(ATs[i]);  //посылаем в монитор порта
    GSMport.println(ATs[i]);  //посылаем в GSM модуль
    delay(d * ATsDelays[i]);
    Serial.println(ReadGSM());  //показываем ответ от GSM модуля
    delay(d);
  }
  Serial.println("GPRG init complete");
}

void gprs_send(String data) {  //Процедура отправки данных на сервер
  //отправка данных на сайт
  int d = 400;
  Serial.println("Send start");
  Serial.println("setup url");
  GSMport.println("AT+HTTPPARA=\"URL\",\"http://hackathonweb.azurewebsites.net/api/sign/create?" + data + "\"");
  delay(d * 2);
  Serial.println(ReadGSM());
  delay(d);
  Serial.println("GET url");
  GSMport.println("AT+HTTPACTION=0");
  delay(d * 2);
  Serial.println(ReadGSM());
  delay(d);
  Serial.println("Send done");
}

String ReadGSM() {  //функция чтения данных от GSM модуля
  int c;
  String v;
  while (GSMport.available()) {  //сохраняем входную строку в переменную v
    c = GSMport.read();
    v += char(c);
    delay(10);
  }
  return v;
}
