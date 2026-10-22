-- Pégalo una vez en Supabase → SQL Editor → New query → Run.
-- Es el mismo proyecto que usa el Habit Tracker; esta tabla es solo para Finanzas.
-- Crea la tabla donde cada cuenta guarda sus finanzas y la regla de que
-- cada persona solo puede leer y escribir lo suyo.

create table if not exists public.finanzas (
  user_id    uuid primary key references auth.users (id) on delete cascade,
  data       jsonb not null,
  updated_at timestamptz not null default now()
);

alter table public.finanzas enable row level security;

drop policy if exists "cada quien lo suyo" on public.finanzas;
create policy "cada quien lo suyo" on public.finanzas
  for all to authenticated
  using (auth.uid() = user_id)
  with check (auth.uid() = user_id);
