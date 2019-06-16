using Microsoft.EntityFrameworkCore.Migrations;

namespace HackathonWeb.Migrations
{
    public partial class AddDbMigration4 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "FinanceArea",
                table: "Work",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "FinanceFederal",
                table: "Work",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "FinanceLocal",
                table: "Work",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "WorkDetails",
                table: "Work",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "FinanceArea",
                table: "Work");

            migrationBuilder.DropColumn(
                name: "FinanceFederal",
                table: "Work");

            migrationBuilder.DropColumn(
                name: "FinanceLocal",
                table: "Work");

            migrationBuilder.DropColumn(
                name: "WorkDetails",
                table: "Work");
        }
    }
}
