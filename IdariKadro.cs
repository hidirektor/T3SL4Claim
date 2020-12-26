using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Proje_Ödevi
{
    class IdariKadro:Calisan
    {
        public void IdariKadroDeğişkenAta(string ad, string soyad,decimal maas,string izinGünü)
        {
            this.Ad = ad;
            this.Soyad = soyad;
            this.Maas = maas;
            this.IzınGunu = izinGünü;
        }
        public string[] IdariKadroListele()
        {
            string[] bilgiler = { this.Ad, this.Soyad,this.Maas.ToString(),this.IzınGunu };
            return bilgiler;
        }
    }
}
