import type { Metadata } from "next";
import { Oswald } from 'next/font/google'

import "./globals.css";

export const metadata: Metadata = {
  title: "inspeção-rei",
  description: "App para gerar inspeção para REI Cemig",
  
};

const oswald = Oswald({ 
  subsets: ['latin'],
  display: 'swap',
  variable: '--font-oswald',
})

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className={`${oswald.variable}`}>
      <body className="bg-gradient-to-r from-slate-500 to-slate-200 text-black max-w-screen pt-15 flex justify-center flex min-h-screen mx-2">
        {children}
      </body>
    </html>
  );
}
