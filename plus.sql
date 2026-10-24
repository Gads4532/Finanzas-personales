-- Finanzas Plus. Pégalo una vez en Supabase → SQL Editor → New query → Run.
-- Una fila por persona con hasta cuándo tiene Plus. La app solo la LEE (la regla de abajo
-- no permite escribir desde la app); se escribe desde este editor o desde el webhook de pago.

create table if not exists public.plus (
  user_id    uuid primary key references auth.users (id) on delete cascade,
  plan       text not null default 'mes',          -- 'mes' | 'vida'
  hasta      timestamptz not null,                 -- Plus activo mientras sea futuro
  origen     text,                                 -- 'manual', 'binance', 'pagomovil', 'lemonsqueezy'…
  updated_at timestamptz not null default now()
);

alter table public.plus enable row level security;

drop policy if exists "leer lo suyo" on public.plus;
create policy "leer lo suyo" on public.plus
  for select to authenticated
  using (auth.uid() = user_id);

-- ---------------------------------------------------------------------------
-- Activar Plus a mano (cuando alguien paga por Binance Pay o Pago Móvil):
-- cambia el correo, el plan ('mes' → un mes desde hoy; 'vida' → para siempre) y ejecuta.
--
-- insert into public.plus (user_id, plan, hasta, origen)
-- select id, 'mes', now() + interval '1 month', 'pagomovil' from auth.users where email = 'correo@ejemplo.com'
-- on conflict (user_id) do update
--   set plan = excluded.plan, hasta = greatest(public.plus.hasta, excluded.hasta), origen = excluded.origen, updated_at = now();
--
-- De por vida:
-- insert into public.plus (user_id, plan, hasta, origen)
-- select id, 'vida', '2099-12-31', 'binance' from auth.users where email = 'correo@ejemplo.com'
-- on conflict (user_id) do update set plan = 'vida', hasta = '2099-12-31', origen = excluded.origen, updated_at = now();
--
-- Quitar Plus:
-- delete from public.plus where user_id = (select id from auth.users where email = 'correo@ejemplo.com');
