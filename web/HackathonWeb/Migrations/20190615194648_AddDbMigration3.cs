using Microsoft.EntityFrameworkCore.Migrations;

namespace HackathonWeb.Migrations
{
    public partial class AddDbMigration3 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<decimal>(
                name: "PlannedEndLat",
                table: "Work",
                type: "decimal(8, 6)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "PlannedEndLng",
                table: "Work",
                type: "decimal(8, 6)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "PlannedStartLat",
                table: "Work",
                type: "decimal(8, 6)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "PlannedStartLng",
                table: "Work",
                type: "decimal(8, 6)",
                nullable: false,
                defaultValue: 0m);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "PlannedEndLat",
                table: "Work");

            migrationBuilder.DropColumn(
                name: "PlannedEndLng",
                table: "Work");

            migrationBuilder.DropColumn(
                name: "PlannedStartLat",
                table: "Work");

            migrationBuilder.DropColumn(
                name: "PlannedStartLng",
                table: "Work");
        }
    }
}
