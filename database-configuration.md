# Database Configuration for IDE

## Connection Details
- **Host**: localhost or 127.0.0.1
- **Port**: 3306
- **Database**: skillbox_hibernate
- **Username**: root
- **Password**: 24022002
- **URL**: jdbc:mysql://localhost:3306/skillbox_hibernate

## Steps to Configure Data Source in IntelliJ IDEA:
1. Open Database window (View → Tool Windows → Database)
2. Click the "+" icon → Data Source → MySQL
3. Configure connection:
   - Host: localhost
   - Port: 3306
   - Database: skillbox_hibernate
   - User: root
   - Password: 24022002
4. Test connection and click OK

## Alternative: Disable the Inspection
If you don't need SQL assistance, you can disable the inspection:
1. Place cursor on the SQL string at line 17
2. Press Alt+Enter
3. Select "Disable inspection"
4. Choose scope (Current file or All files)
