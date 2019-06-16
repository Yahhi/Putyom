using System;
using System.Globalization;

namespace HackathonWeb
{
    public static class Helpers
    {
        public static string ToKMB(this decimal num)
        {
            if (num > 999999 || num < -999999)
            {
                return num.ToString("0,,.## млн", CultureInfo.InvariantCulture);
            }
            else
            if (num > 999 || num < -999)
            {
                return num.ToString("0,.# тыс", CultureInfo.InvariantCulture);
            }
            else
            {
                return num.ToString(CultureInfo.InvariantCulture);
            }
        }
    }
}
