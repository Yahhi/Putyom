using Microsoft.EntityFrameworkCore.Migrations;

namespace HackathonWeb.Migrations
{
    public partial class FixDecimalPrecision : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<decimal>(
                name: "Lng",
                table: "SignInfos",
                type: "decimal(8, 6)",
                nullable: false,
                oldClrType: typeof(decimal));

            migrationBuilder.AlterColumn<decimal>(
                name: "Lat",
                table: "SignInfos",
                type: "decimal(8, 6)",
                nullable: false,
                oldClrType: typeof(decimal));
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<decimal>(
                name: "Lng",
                table: "SignInfos",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(8, 6)");

            migrationBuilder.AlterColumn<decimal>(
                name: "Lat",
                table: "SignInfos",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(8, 6)");
        }
    }
}
