INSERT INTO public.users (id, first_name, last_name)
SELECT nextval('public.users_seq'), 'John', 'Triantafyllakis'
    WHERE NOT EXISTS (
    SELECT 1 FROM public.users
    WHERE first_name = 'John' AND last_name = 'Triantafyllakis'
);

INSERT INTO public.wallet (balance, id, user_id, state)
SELECT 0.00, nextval('public.wallet_seq'), u.id, 'AVAILABLE_BALANCE'
FROM public.users u
WHERE u.first_name = 'John' AND u.last_name = 'Triantafyllakis'
  AND NOT EXISTS (
    SELECT 1 FROM public.wallet w
    WHERE w.user_id = u.id
);

INSERT INTO public.users (id, first_name, last_name)
SELECT nextval('public.users_seq'), 'John2', 'Triantafyllakis2'
    WHERE NOT EXISTS (
    SELECT 1 FROM public.users
    WHERE first_name = 'John2' AND last_name = 'Triantafyllakis2'
);