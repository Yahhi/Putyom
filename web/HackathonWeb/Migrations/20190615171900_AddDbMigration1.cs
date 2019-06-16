using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace HackathonWeb.Migrations
{
    public partial class AddDbMigration1 : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Work_Contractors_ContractorId",
                table: "Work");

            migrationBuilder.DropColumn(
                name: "DeviceId",
                table: "SignInfos");

            migrationBuilder.AlterColumn<int>(
                name: "ContractorId",
                table: "Work",
                nullable: false,
                oldClrType: typeof(int),
                oldNullable: true);

            migrationBuilder.AddColumn<DateTime>(
                name: "ActualEnd",
                table: "Work",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<DateTime>(
                name: "ActualStart",
                table: "Work",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<DateTime>(
                name: "Created",
                table: "Work",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<DateTime>(
                name: "PlannedEnd",
                table: "Work",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<DateTime>(
                name: "PlannedStart",
                table: "Work",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<DateTime>(
                name: "Created",
                table: "SignInfos",
                nullable: false,
                defaultValue: new DateTime(1, 1, 1, 0, 0, 0, 0, DateTimeKind.Unspecified));

            migrationBuilder.AddColumn<int>(
                name: "SignId",
                table: "SignInfos",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "WorkId",
                table: "SignInfos",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "WorkId",
                table: "Sign",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "WorkId",
                table: "CameraInfos",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_SignInfos_SignId",
                table: "SignInfos",
                column: "SignId");

            migrationBuilder.CreateIndex(
                name: "IX_SignInfos_WorkId",
                table: "SignInfos",
                column: "WorkId");

            migrationBuilder.CreateIndex(
                name: "IX_Sign_WorkId",
                table: "Sign",
                column: "WorkId");

            migrationBuilder.CreateIndex(
                name: "IX_CameraInfos_WorkId",
                table: "CameraInfos",
                column: "WorkId");

            migrationBuilder.AddForeignKey(
                name: "FK_CameraInfos_Work_WorkId",
                table: "CameraInfos",
                column: "WorkId",
                principalTable: "Work",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Sign_Work_WorkId",
                table: "Sign",
                column: "WorkId",
                principalTable: "Work",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_SignInfos_Sign_SignId",
                table: "SignInfos",
                column: "SignId",
                principalTable: "Sign",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_SignInfos_Work_WorkId",
                table: "SignInfos",
                column: "WorkId",
                principalTable: "Work",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);

            migrationBuilder.AddForeignKey(
                name: "FK_Work_Contractors_ContractorId",
                table: "Work",
                column: "ContractorId",
                principalTable: "Contractors",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_CameraInfos_Work_WorkId",
                table: "CameraInfos");

            migrationBuilder.DropForeignKey(
                name: "FK_Sign_Work_WorkId",
                table: "Sign");

            migrationBuilder.DropForeignKey(
                name: "FK_SignInfos_Sign_SignId",
                table: "SignInfos");

            migrationBuilder.DropForeignKey(
                name: "FK_SignInfos_Work_WorkId",
                table: "SignInfos");

            migrationBuilder.DropForeignKey(
                name: "FK_Work_Contractors_ContractorId",
                table: "Work");

            migrationBuilder.DropIndex(
                name: "IX_SignInfos_SignId",
                table: "SignInfos");

            migrationBuilder.DropIndex(
                name: "IX_SignInfos_WorkId",
                table: "SignInfos");

            migrationBuilder.DropIndex(
                name: "IX_Sign_WorkId",
                table: "Sign");

            migrationBuilder.DropIndex(
                name: "IX_CameraInfos_WorkId",
                table: "CameraInfos");

            migrationBuilder.DropColumn(
                name: "ActualEnd",
                table: "Work");

            migrationBuilder.DropColumn(
                name: "ActualStart",
                table: "Work");

            migrationBuilder.DropColumn(
                name: "Created",
                table: "Work");

            migrationBuilder.DropColumn(
                name: "PlannedEnd",
                table: "Work");

            migrationBuilder.DropColumn(
                name: "PlannedStart",
                table: "Work");

            migrationBuilder.DropColumn(
                name: "Created",
                table: "SignInfos");

            migrationBuilder.DropColumn(
                name: "SignId",
                table: "SignInfos");

            migrationBuilder.DropColumn(
                name: "WorkId",
                table: "SignInfos");

            migrationBuilder.DropColumn(
                name: "WorkId",
                table: "Sign");

            migrationBuilder.DropColumn(
                name: "WorkId",
                table: "CameraInfos");

            migrationBuilder.AlterColumn<int>(
                name: "ContractorId",
                table: "Work",
                nullable: true,
                oldClrType: typeof(int));

            migrationBuilder.AddColumn<string>(
                name: "DeviceId",
                table: "SignInfos",
                nullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Work_Contractors_ContractorId",
                table: "Work",
                column: "ContractorId",
                principalTable: "Contractors",
                principalColumn: "Id",
                onDelete: ReferentialAction.Restrict);
        }
    }
}
