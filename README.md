## Dodawanie fantasii jako submoduł do istniejącego projektu
	
	git submodule add https://gitlab.13facesgames.com/fantasia/java.git src/main/java/io/fantasia

		git submodule add <adres> <ścieżka>
		Adres powinien być w formie https a nie ssh, inaczej osoby które nie wprowadziły swojego klucza publicznego w ustawieniach gitlaba nie będą miały dostępu do submodułu.

## Klonowanie projektu korzystającego z submodułu
	
	git clone <adres>
	git submodule update --init --recursive

		git clone skopiuje projekt główny (w tym przypadku example) oraz jego ustawienia, utworzy równierz katalog w którym ma być submoduł ale będzie on pusty.
		Aby pobrać submoduł należy użyć git submodule update --init --recursive

## Update submodułu

	git submodule update --recursive --remote
	