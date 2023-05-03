declare module "elec-ab" {
  interface i {
    a: string,
    b: () => void
  }

  const d: i;
  export default d;
  export import aa = NS.nsi
}

declare namespace NS{
  export const nsa: string;
  interface k{
    im: string
  }
  export const nsi: k;
}

declare type num = number;