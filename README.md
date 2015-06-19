# wMessages
>Fetch whatsapp messages and save to a excel file

1. Install and configure Maven: http://www.mkyong.com/maven/how-to-install-maven-in-windows/
2. Install Firefox
3. Setup a profile for Firefox with name “myProfile” and save login session of web.whatsapp.com into same
4. Run project through command line using below mvn command:

```bash
mvn clean test
```

5. If you want to load earlier messages please below mvn command:

```bash
mvn clean test -DloadPrevious=true
```

6. After test execution resultant excel data file is available at:

```bash
./wMessages/output/Messages.xls
```

