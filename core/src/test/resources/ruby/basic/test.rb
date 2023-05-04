class A
  $i = 1

  def _func(a)
    if $i > 0
      $i = 2
    else
      $i = 0
    end
    puts a
  end

end
