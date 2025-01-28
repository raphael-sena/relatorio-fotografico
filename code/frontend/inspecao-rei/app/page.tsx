import Image from "next/image";
import Link from "next/link";

export default function Home() {
  return (
    <div className="flex min-h-screen mx-2">
      <div>
        <h1 className="mb-6 text-3xl">Seja bem-vindo ao GRI</h1>
        <div className="flex-col sm:flex-col-2 space-y-4 sm:space-y-0 sm:space-x-4">
          <button className="py-6 px-4 rounded-xl bg-slate-200 drop-shadow-xl opacity-70 hover:opacity-100">
            <Link href="/relatorio" className="flex items-center justify-center gap-2 ">
              <Image
                src="/icons/document-add-svgrepo-com.svg"
                width={40}
                height={40}
                alt="Picture of the author"
              />
              <p className="text-center text-xl">Novo Relatório</p>
            </Link>
          </button>
          <button className="py-6 px-4 rounded-xl bg-slate-200 drop-shadow-xl opacity-70 hover:opacity-100">
            <Link href="/historico" className="flex items-center justify-center gap-2 ">
              <Image
                src="/icons/document-1-svgrepo-com.svg"
                width={40}
                height={40}
                alt="Picture of the author"
              />
              <p className="text-center text-xl">Histórico de Relatórios</p>
            </Link>
          </button>
        </div>
      </div>
    </div>
  );
}
